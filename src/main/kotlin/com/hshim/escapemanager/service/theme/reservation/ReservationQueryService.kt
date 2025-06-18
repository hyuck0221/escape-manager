package com.hshim.escapemanager.service.theme.reservation

import com.hshim.escapemanager.database.theme.reservation.repository.ReservationRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.reservation.ReservationResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import util.DateUtil.stringToDate

@Service
@Transactional(readOnly = true)
class ReservationQueryService(private val reservationRepository: ReservationRepository) {
    fun findById(themeId: String, id: String): ReservationResponse {
        return reservationRepository.findByThemeIdAndId(themeId, id)?.let { ReservationResponse(it) }
            ?: throw GlobalException.NOT_FOUND_RESERVATION.exception
    }

    fun findAllPageBy(themeId: String, date: String, search: String?, pageable: Pageable): Page<ReservationResponse> {
        return when (search == null) {
            true -> reservationRepository.findAllByThemeIdAndDate(themeId, date.stringToDate(), pageable)
            false -> reservationRepository.findAllByThemeIdAndDateAndSearch(
                themeId = themeId,
                date = date.stringToDate(),
                search = search,
                pageable = pageable
            )
        }.map { ReservationResponse(it) }
    }
}