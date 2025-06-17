package com.hshim.escapemanager.security.service

import com.hshim.escapemanager.database.account.repository.AccountRepository
import com.hshim.escapemanager.exception.GlobalException
import com.hshim.escapemanager.security.model.DomainUserPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val accountRepository: AccountRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return accountRepository.findByLoginId(username)
            ?.let { DomainUserPrincipal(it) }
            ?: throw GlobalException.NOT_FOUND_ACCOUNT.exception
    }
}