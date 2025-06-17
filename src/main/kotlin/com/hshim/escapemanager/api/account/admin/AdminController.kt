package com.hshim.escapemanager.api.account.admin

import com.hshim.escapemanager.annotation.PublicEndpoint
import com.hshim.escapemanager.model.account.admin.AdminRequest
import com.hshim.escapemanager.model.account.admin.AdminResponse
import com.hshim.escapemanager.service.account.admin.AdminCommandService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/account/admin")
class AdminController(private val adminCommandService: AdminCommandService) {

    @PublicEndpoint
    @PostMapping
    fun init(
        @RequestParam(required = true) centerId: String,
        @RequestBody request: AdminRequest,
    ): AdminResponse {
        return adminCommandService.init(centerId, request)
    }
}