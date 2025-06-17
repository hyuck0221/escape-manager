package com.hshim.escapemanager.database.account

import com.hshim.escapemanager.database.account.enums.Role
import com.hshim.escapemanager.database.base.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorColumn
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
import util.CommonUtil.ulid

@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "role")
class Account (
    @Id
    @Column(columnDefinition = "varchar(36)")
    val id: String = ulid(),

    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var loginId: String?,

    @Column(nullable = true)
    var password: String?,

    @Column(nullable = true)
    var description: String? = null,

    @Column(nullable = false, columnDefinition = "varchar(255)", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    var role: Role,
): BaseTimeEntity()