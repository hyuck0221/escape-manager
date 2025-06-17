package com.hshim.escapemanager.database.theme

import com.hshim.escapemanager.database.base.BaseTimeEntity
import com.hshim.escapemanager.database.center.Center
import com.hshim.escapemanager.database.theme.enums.ThemeCategory
import jakarta.persistence.*
import util.CommonUtil.ulid
import java.time.LocalTime

@Entity
@Table(name = "theme")
class Theme(
    @Id
    @Column(columnDefinition = "varchar(36)")
    val id: String = ulid(),

    @ManyToOne(targetEntity = Center::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id", nullable = false, columnDefinition = "varchar(36)")
    val center: Center?,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = true)
    var description: String? = null,

    @Column(nullable = true, columnDefinition = "varchar(100)")
    @Enumerated(EnumType.STRING)
    var category: ThemeCategory? = null,

    @Column(nullable = true)
    var difficulty: Double? = null,

    @Column(nullable = true)
    var fear: Double? = null,

    @Column(nullable = true)
    var activity: Double? = null,

    @Column(nullable = false)
    var reservationOpenTime: LocalTime,

    @Column(nullable = false)
    var minPersonCnt: Int,

    @Column(nullable = false)
    var maxPersonCnt: Int,
) : BaseTimeEntity() {

    companion object {
        fun of(id: String) = Theme(
            id = id,
            center = null,
            name = "",
            description = null,
            category = null,
            difficulty = null,
            fear = null,
            activity = null,
            reservationOpenTime = LocalTime.now(),
            minPersonCnt = 0,
            maxPersonCnt = 0,
        )
    }
}