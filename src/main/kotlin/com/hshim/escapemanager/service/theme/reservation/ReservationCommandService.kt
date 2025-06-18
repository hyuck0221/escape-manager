package com.hshim.escapemanager.service.theme.reservation

import com.hshim.escapemanager.database.theme.repository.ThemeRepository
import com.hshim.escapemanager.database.theme.reservation.repository.ReservationRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.reservation.ReservationRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import util.DateUtil.stringToDate

@Service
@Transactional
class ReservationCommandService(
    private val reservationRepository: ReservationRepository,
    private val themeRepository: ThemeRepository,
    private val reservationProcessor: ReservationProcessor,
) {
    fun initTask(themeId: String, userId: String?, taskId: String, request: ReservationRequest): Boolean {
        val theme = themeRepository.findByIdOrNull(themeId)
            ?.apply { this.validateReserveTime(request.datetime.stringToDate("yyyy-MM-dd HH:mm")) }
            ?: throw GlobalException.NOT_FOUND_THEME.exception
        reservationProcessor.addTask(taskId, request.toEntity(theme, userId))
        return true
    }

    fun deleteById(themeId: String, id: String) {
        reservationRepository.deleteByThemeIdAndId(themeId, id)
    }

    fun deleteByCode(themeId: String, code: String) {
        reservationRepository.findByThemeIdAndCode(themeId, code)
            ?.let {
                it.validateUser()
                reservationRepository.delete(it)
            } ?: throw GlobalException.NOT_FOUND_RESERVATION.exception
    }

    fun deleteTask(taskId: String) = reservationProcessor.remove(taskId)
}