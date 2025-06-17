package com.hshim.escapemanager.database.account.admin

import com.hshim.escapemanager.database.account.Account
import com.hshim.escapemanager.database.account.enums.Role
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.Table
import util.CommonUtil.ulid

@Entity
@Table(name = "super_admin")
@DiscriminatorValue(value = "SUPER_ADMIN")
class SuperAdmin(
    id: String = ulid(),
    name: String,
    loginId: String,
    password: String,
    description: String?,
) : Account(
    id = id,
    name = name,
    loginId = loginId,
    password = password,
    description = description,
    role = Role.SUPER_ADMIN,
)