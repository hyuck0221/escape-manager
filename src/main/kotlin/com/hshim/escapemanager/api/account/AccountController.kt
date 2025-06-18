package com.hshim.escapemanager.api.account

import com.hshim.escapemanager.annotation.role.Public
import com.hshim.escapemanager.model.account.AccountLoginRequest
import com.hshim.escapemanager.model.account.AccountLoginResponse
import com.hshim.escapemanager.service.account.AccountQueryService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/account")
class AccountController(private val accountQueryService: AccountQueryService) {

    @Public
    @PostMapping("/login")
    fun login(@RequestBody request: AccountLoginRequest): AccountLoginResponse {
        return accountQueryService.login(request)
    }
}