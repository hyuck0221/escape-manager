package com.hshim.escapemanager.database.center

import com.hshim.escapemanager.database.base.BaseTimeEntity
import com.hshim.escapemanager.database.theme.Theme
import jakarta.persistence.*
import util.CommonUtil.ulid

@Entity
@Table(name = "center")
class Center (
    @Id
    @Column(columnDefinition = "varchar(36)")
    val id: String = ulid(),

    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var description: String? = null,

    @Column(nullable = true)
    var address: String? = null,

    @Column(nullable = true)
    var phoneNo: String? = null,

    @Column(nullable = true)
    var email: String? = null,

    @Column(nullable = false)
    var isDeleted: Boolean = false,
): BaseTimeEntity() {
    @OneToMany(targetEntity = Theme::class, mappedBy = "center", cascade = [CascadeType.ALL], orphanRemoval = true)
    val themes: MutableSet<Theme> = mutableSetOf()
}