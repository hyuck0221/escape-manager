package com.hshim.escapemanager.service.account

import com.hshim.escapemanager.database.account.repository.AccountRepository
import com.hshim.escapemanager.model.account.AccountLoginRequest
import com.hshim.escapemanager.model.account.AccountLoginResponse
import com.hshim.escapemanager.security.component.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AccountQueryService(
    private val accountRepository: AccountRepository,
    private val authenticationManager: AuthenticationManager,
    private val tokenProvider: JwtTokenProvider,
) {
    fun login(request: AccountLoginRequest): AccountLoginResponse {
        val roles =
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.loginId, request.password))
                .authorities.map { it.authority.removePrefix("ROLE_") }
        val account = accountRepository.findByLoginId(request.loginId)!!
        val token = tokenProvider.generateToken(account, roles)
        return AccountLoginResponse(accessToken = token, role = roles.first())
    }
}