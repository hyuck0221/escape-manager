package com.hshim.escapemanager.api.theme.reservation

import com.hshim.escapemanager.annotation.role.Role
import com.hshim.escapemanager.database.account.enums.Role.ADMIN
import com.hshim.escapemanager.database.account.enums.Role.USER
import com.hshim.escapemanager.model.reservation.ReservationRequest
import com.hshim.escapemanager.model.reservation.ReservationResponse
import com.hshim.escapemanager.service.theme.reservation.ReservationCommandService
import com.hshim.escapemanager.service.theme.reservation.ReservationQueryService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/theme/{themeId}/reservation")
class ReservationController(
    private val reservationQueryService: ReservationQueryService,
    private val reservationCommandService: ReservationCommandService,
) {
    @Role([ADMIN, USER])
    @GetMapping("/{id}")
    fun findById(
        @PathVariable themeId: String,
        @PathVariable id: String
    ): ReservationResponse {
        return reservationQueryService.findById(themeId, id)
    }

    @Role([ADMIN, USER])
    @GetMapping("/list")
    fun findAllPageBy(
        @PathVariable themeId: String,
        @RequestParam(required = true) date: String,
        @RequestParam(required = false) search: String?,
        pageable: Pageable,
    ): Page<ReservationResponse> {
        return reservationQueryService.findAllPageBy(themeId, date, search, pageable)
    }

    @Role([USER])
    @PostMapping
    fun init(
        @PathVariable themeId: String,
        @RequestBody request: ReservationRequest,
    ): ReservationResponse {
        return reservationCommandService.init(themeId, request)
    }

    @Role([ADMIN, USER])
    @PutMapping("/{id}")
    fun update(
        @PathVariable themeId: String,
        @PathVariable id: String,
        @RequestBody request: ReservationRequest,
    ): ReservationResponse {
        return reservationCommandService.update(themeId, id, request)
    }

    @Role([ADMIN, USER])
    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable themeId: String,
        @PathVariable id: String,
    ) = reservationCommandService.delete(themeId, id)
}