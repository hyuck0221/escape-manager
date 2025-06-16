package com.hshim.escapemanager.database.account.admin

import com.hshim.escapemanager.database.base.BaseTimeEntity
import com.hshim.escapemanager.database.center.Center
import jakarta.persistence.*
import util.CommonUtil.ulid

@Entity
@Table(name = "admin")
class Admin (
    @Id
    @Column(columnDefinition = "varchar(36)")
    val id: String = ulid(),

    @ManyToOne(targetEntity = Center::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false, columnDefinition = "varchar(36)")
    var center: Center,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var description: String? = null,

    @Column(nullable = false)
    var isDeleted: Boolean = false,
): BaseTimeEntity()