package com.hshim.escapemanager.service.theme.reservation

import com.hshim.escapemanager.database.theme.reservation.repository.ReservationRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.reservation.ReservationRequest
import com.hshim.escapemanager.model.reservation.ReservationResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReservationCommandService(private val reservationRepository: ReservationRepository) {
    fun init(themeId: String, request: ReservationRequest): ReservationResponse {
        return ReservationResponse(reservationRepository.save(request.toEntity(themeId)))
    }

    fun update(themeId: String, id: String, request: ReservationRequest): ReservationResponse {
        return reservationRepository.findByThemeIdAndId(themeId, id)
            ?.apply { request.updateTo(this) }
            ?.let { ReservationResponse(it) }
            ?: throw GlobalException.NOT_FOUND_RESERVATION.exception
    }

    fun delete(themeId: String, id: String) {
        reservationRepository.deleteByThemeIdAndId(themeId, id)
    }
}