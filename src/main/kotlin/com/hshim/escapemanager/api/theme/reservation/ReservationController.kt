package com.hshim.escapemanager.api.theme.reservation

import com.hshim.escapemanager.annotation.role.Public
import com.hshim.escapemanager.annotation.role.Role
import com.hshim.escapemanager.common.token.context.ContextUtil.getAccountId
import com.hshim.escapemanager.common.token.context.ContextUtil.getContext
import com.hshim.escapemanager.common.token.context.ContextUtil.getRole
import com.hshim.escapemanager.database.account.enums.Role.ADMIN
import com.hshim.escapemanager.database.account.enums.Role.USER
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.model.reservation.ReservationRequest
import com.hshim.escapemanager.model.reservation.ReservationResponse
import com.hshim.escapemanager.service.theme.reservation.ReservationCommandService
import com.hshim.escapemanager.service.theme.reservation.ReservationQueryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.time.YearMonth


@RestController
@RequestMapping("/theme/{themeId}/reservation")
class ReservationController(
    private val reservationQueryService: ReservationQueryService,
    private val reservationCommandService: ReservationCommandService,
) {
    @Role([ADMIN])
    @GetMapping("/{id}")
    fun findById(
        @PathVariable themeId: String,
        @PathVariable id: String
    ): ReservationResponse {
        return reservationQueryService.findById(themeId, id)
    }

    @Public
    @GetMapping("/code/{code}")
    fun findByCode(
        @PathVariable themeId: String,
        @PathVariable code: String,
    ): ReservationResponse {
        return reservationQueryService.findByCode(themeId, code)
    }

    @Public
    @GetMapping("/list")
    fun findAllPageBy(
        @PathVariable themeId: String,
        @RequestParam(required = true) yearMonth: YearMonth,
        @RequestParam(required = false) search: String?,
        pageable: Pageable,
    ): Page<ReservationResponse> {
        return reservationQueryService.findAllPageBy(themeId, yearMonth, search, pageable)
    }

    @Public
    @PostMapping("/task/{taskId}")
    fun initTask(
        @PathVariable themeId: String,
        @PathVariable taskId: String,
        @RequestBody request: ReservationRequest,
    ) {
        val userId = if (getContext() != null && getRole() == USER) getAccountId() else null
        val success = reservationCommandService.initTask(themeId, userId, taskId, request)
        if (success) throw GlobalException.CREATED.exception
    }

    @Public
    @GetMapping("/task/{taskId}", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun connectTask(
        @PathVariable themeId: String,
        @PathVariable taskId: String,
    ): SseEmitter {
        return reservationQueryService.connectTask(taskId)
    }

    @Public
    @DeleteMapping("/task/{taskId}")
    fun deleteTask(
        @PathVariable themeId: String,
        @PathVariable taskId: String,
    ) = reservationCommandService.deleteTask(taskId)


    @Role([ADMIN])
    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable themeId: String,
        @PathVariable id: String,
    ) = reservationCommandService.deleteById(themeId, id)

    @Public
    @DeleteMapping("/code/{code}")
    fun deleteByCode(
        @PathVariable themeId: String,
        @PathVariable code: String,
    ) = reservationCommandService.deleteByCode(themeId, code)
}