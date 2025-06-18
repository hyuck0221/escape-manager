package com.hshim.escapemanager.database.account.user

import com.hshim.escapemanager.database.account.Account
import com.hshim.escapemanager.database.account.enums.Role
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table
import util.CommonUtil.ulid

@Entity
@Table(name = "user")
@DiscriminatorValue(value = "USER")
class User(
    id: String = ulid(),
    name: String,
    loginId: String?,
    password: String?,
    description: String?,
) : Account(
    id = id,
    name = name,
    loginId = loginId,
    password = password,
    description = description,
    role = Role.USER,
) {
    companion object {
        fun of(id: String) = User(
            id = id,
            name = "",
            loginId = null,
            password = null,
            description = null,
        )
    }
}