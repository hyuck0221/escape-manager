package com.hshim.escapemanager.security.model

import com.hshim.escapemanager.database.account.Account
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class DomainUserPrincipal(val account: Account) : UserDetails {
    override fun getUsername() = account.loginId
    override fun getPassword() = account.password
    override fun getAuthorities() = listOf(SimpleGrantedAuthority("ROLE_${account.role.name}"))
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}