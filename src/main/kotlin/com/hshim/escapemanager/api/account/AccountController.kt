package com.hshim.escapemanager.api.account

import com.hshim.escapemanager.annotation.role.Public
import com.hshim.escapemanager.annotation.role.Role
import com.hshim.escapemanager.database.account.enums.Role.*
import com.hshim.escapemanager.model.account.AccountLoginRequest
import com.hshim.escapemanager.model.account.AccountLoginResponse
import com.hshim.escapemanager.service.account.AccountQueryService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/account")
class AccountController(private val accountQueryService: AccountQueryService) {

    @Public
    @PostMapping("/login")
    fun login(@RequestBody request: AccountLoginRequest): AccountLoginResponse {
        return accountQueryService.login(request)
    }

    @Role([SUPER_ADMIN, ADMIN, USER])
    @GetMapping("/login-id/{loginId}/validate")
    fun validateLoginId(@PathVariable loginId: String) {
        return accountQueryService.validateLoginId(loginId)
    }
}