package com.hshim.escapemanager.service.theme.reservation

import com.hshim.escapemanager.common.token.context.ContextUtil.getAccountId
import com.hshim.escapemanager.common.token.context.ContextUtil.getContext
import com.hshim.escapemanager.common.token.context.ContextUtil.getRole
import com.hshim.escapemanager.database.account.enums.Role
import com.hshim.escapemanager.database.theme.reservation.repository.ReservationRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.reservation.ReservationResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class ReservationQueryService(
    private val reservationRepository: ReservationRepository,
    private val reservationProcessor: ReservationProcessor,
) {
    fun findById(themeId: String, id: String): ReservationResponse {
        return reservationRepository.findByThemeIdAndId(themeId, id)?.let { ReservationResponse.detail(it) }
            ?: throw GlobalException.NOT_FOUND_RESERVATION.exception
    }

    fun findByCode(themeId: String, code: String): ReservationResponse {
        return reservationRepository.findByThemeIdAndCode(themeId, code)?.let { ReservationResponse.detail(it) }
            ?: throw GlobalException.NOT_FOUND_RESERVATION.exception
    }

    fun findAllPageBy(
        themeId: String,
        yearMonth: YearMonth,
        search: String?,
        pageable: Pageable
    ): Page<ReservationResponse> {
        return when (search == null) {
            true -> reservationRepository.findAllByThemeIdAndYearMonth(themeId, yearMonth, pageable)
            false -> reservationRepository.findAllByThemeIdAndDateAndSearch(
                themeId = themeId,
                yearMonth = yearMonth,
                search = search,
                pageable = pageable
            )
        }.map {
            when {
                getContext() == null -> ReservationResponse.simple(it)
                getRole() != Role.USER -> ReservationResponse.detail(it)
                getRole() == Role.USER && getAccountId() == it.user?.id -> ReservationResponse.detail(it)
                else -> ReservationResponse.simple(it)
            }
        }
    }

    fun connectTask(taskId: String) = reservationProcessor.sseConnect(taskId)
}