package com.hshim.escapemanager.service.account

import com.hshim.escapemanager.model.account.AccountLoginRequest
import com.hshim.escapemanager.model.account.AccountLoginResponse
import com.hshim.escapemanager.security.component.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AccountQueryService(
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: JwtTokenProvider,
) {
    fun login(request: AccountLoginRequest): AccountLoginResponse {
        val roles =
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.loginId, request.password))
                .authorities.map { it.authority.removePrefix("ROLE_") }
        val token = tokenProvider.generateToken(request.loginId, roles)
        return AccountLoginResponse(accessToken = token, role = roles.first())
    }
}