package com.hshim.escapemanager.service.theme.reservation

import com.hshim.escapemanager.common.sse.SSEUtil.emitterSend
import com.hshim.escapemanager.database.theme.reservation.Reservation
import com.hshim.escapemanager.database.theme.reservation.repository.ReservationLogRepository
import com.hshim.escapemanager.database.theme.reservation.repository.ReservationRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.reservation.ReservationSSEResponse
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.logging.Logger

@Service
class ReservationProcessor(
    @Value("\${thread.reservation.interval}") val interval: Long,
    @Value("\${thread.reservation.time-unit}") val timeUnit: TimeUnit,
    private val reservationRepository: ReservationRepository,
    private val reservationLogRepository: ReservationLogRepository,
) {
    private val logger: Logger = Logger.getLogger(ReservationProcessor::class.java.name)
    private val queue: Queue<Pair<Reservation, String>> = LinkedList()
    private val taskEmitter: ConcurrentHashMap<String, SseEmitter> = ConcurrentHashMap()
    private val executor = Executors.newSingleThreadScheduledExecutor { r -> Thread(r, "reservation-processor") }

    @PostConstruct
    fun startBackgroundLoop() {
        logger.info("Starting background loop: interval=$interval $timeUnit")
        executor.scheduleAtFixedRate({ process() }, 0, interval, timeUnit)
    }

    @PreDestroy
    fun shutdownExecutor() {
        logger.info("Shutting down ReservationProcessor executor")
        executor.shutdownNow()
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun process() {
        if (queue.isEmpty()) return
        logger.info("process() invoked — queue size=${queue.size}")
        val (reservation, taskId) = queue.poll()
        try {
            val reservingTimes = reservationRepository.findAllByThemeIdAndDatetime(
                themeId = reservation.theme.id,
                datetime = reservation.datetime,
            ).map { it.datetime }

            if (reservingTimes.contains(reservation.datetime))
                throw GlobalException.IS_ALREADY_RESERVE_TIME.exception

            reservationRepository.save(reservation)
            reservationLogRepository.save(reservation.toLogEntity(taskId, null))
            taskEmitter[taskId]?.emitterSend(ReservationSSEResponse.Success(reservation.code))
            logger.info("Reservation ${reservation.code} (task $taskId) saved successfully")
        } catch (e: Exception) {
            logger.warning("Failed reservation ${reservation.code} (task $taskId): ${e.message}")
            reservationLogRepository.save(reservation.toLogEntity(taskId, e))
            val reason = when (e) {
                is ResponseStatusException -> e.message
                else -> "서버 에러로 인해 예약이 실패하였습니다."
            }
            taskEmitter[taskId]?.emitterSend(ReservationSSEResponse.Fail(reason))
        } finally {
            taskEmitter[taskId]?.complete()
        }
    }

    fun addTask(taskId: String, reservation: Reservation) = queue.add(reservation to taskId)
    fun remove(taskId: String) {
        queue.removeIf { it.second == taskId }
        taskEmitter[taskId]?.apply {
            this.emitterSend(ReservationSSEResponse.Fail("예약이 취소되었습니다."))
            this.complete()
        }
    }

    fun sseConnect(taskId: String): SseEmitter {
        val emitter = SseEmitter(30 * 1000)
        emitter.onCompletion { taskEmitter.remove(taskId) }
        emitter.onTimeout { emitter.complete() }
        emitter.onError { emitter.complete() }
        emitter.send("connect")

        taskEmitter[taskId] = emitter
        return emitter
    }
}