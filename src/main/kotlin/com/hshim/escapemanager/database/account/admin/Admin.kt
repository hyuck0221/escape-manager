package com.hshim.escapemanager.database.account.admin

import com.hshim.escapemanager.database.account.Account
import com.hshim.escapemanager.database.account.enums.Role
import com.hshim.escapemanager.database.center.Center
import jakarta.persistence.*
import util.CommonUtil.ulid

@Entity
@Table(name = "admin")
@DiscriminatorValue(value = "ADMIN")
class Admin(
    id: String = ulid(),
    name: String,
    loginId: String,
    password: String,
    description: String?,

    @ManyToOne(targetEntity = Center::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false, columnDefinition = "varchar(36)")
    var center: Center,
) : Account(
    id = id,
    name = name,
    loginId = loginId,
    password = password,
    description = description,
    role = Role.ADMIN,
)