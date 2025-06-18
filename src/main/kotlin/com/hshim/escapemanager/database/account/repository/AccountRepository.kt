package com.hshim.escapemanager.database.account.repository

import com.hshim.escapemanager.database.account.Account
import com.hshim.escapemanager.database.account.enums.Role
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, String> {
    fun findByLoginId(loginId: String): Account?
    fun existsByIdAndRoleIn(id: String, roles: List<Role>): Boolean
}