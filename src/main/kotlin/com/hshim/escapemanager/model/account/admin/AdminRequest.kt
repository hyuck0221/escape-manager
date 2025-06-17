package com.hshim.escapemanager.model.account.admin

import com.hshim.escapemanager.database.account.admin.Admin
import com.hshim.escapemanager.database.center.Center
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AdminRequest(
    val name: String,
    val loginId: String,
    val password: String,
    val description: String?,
) {
    fun toEntity(centerId: String) = Admin(
        center = Center.of(centerId),
        name = name,
        loginId = loginId,
        password = BCryptPasswordEncoder().encode(password),
        description = description,
    )

    fun updateTo(admin: Admin) {
        admin.name = name
        admin.description = description
    }
}