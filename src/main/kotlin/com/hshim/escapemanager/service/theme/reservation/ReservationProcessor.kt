package com.hshim.escapemanager.service.theme.reservation

import com.hshim.escapemanager.database.theme.reservation.Reservation
import com.hshim.escapemanager.database.theme.reservation.repository.ReservationLogRepository
import com.hshim.escapemanager.database.theme.reservation.repository.ReservationRepository
import com.hshim.escapemanager.exception.GlobalException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import util.CommonUtil.ulid
import java.util.*

@Service
class ReservationProcessor(
    private val reservationRepository: ReservationRepository,
    private val reservationLogRepository: ReservationLogRepository,
) {
    private val queue: Queue<Pair<Reservation, String>> = LinkedList()

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun process() {
        val (reservation, taskId) = queue.poll() ?: return
        try {
            val reservingTimes = reservationRepository.findAllByThemeIdAndDatetime(
                themeId = reservation.theme.id,
                datetime = reservation.datetime,
            ).map { it.datetime }

            if (reservingTimes.contains(reservation.datetime))
                throw GlobalException.IS_ALREADY_RESERVE_TIME.exception

            reservationRepository.save(reservation)
        } catch (e: Exception) {
            reservationLogRepository.save(reservation.toLogEntity(e))
            // TODO 실패 전송
        } finally {
            reservationLogRepository.save(reservation.toLogEntity(null))
            // TODO 성공 전송
        }
    }

    fun add(reservation: Reservation): String {
        val taskId = ulid()
        queue.add(reservation to taskId)
    }

    fun remove(reservation: Reservation) = queue.remove(reservation)
}