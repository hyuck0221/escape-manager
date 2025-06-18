package com.hshim.escapemanager.database.theme.reservation

import com.hshim.escapemanager.database.account.user.User
import com.hshim.escapemanager.database.base.BaseTimeEntity
import com.hshim.escapemanager.database.theme.Theme
import jakarta.persistence.*
import util.CommonUtil.ulid

@Entity
@Table(name = "reservation_log")
class ReservationLog(
    @Id
    @Column(columnDefinition = "varchar(36)")
    val id: String = ulid(),

    @ManyToOne(targetEntity = Theme::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false, columnDefinition = "varchar(36)")
    val theme: Theme,

    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true, columnDefinition = "varchar(36)")
    val user: User?,

    @ManyToOne(targetEntity = Reservation::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = true, columnDefinition = "varchar(36)")
    val reservation: Reservation?,

    @Column(nullable = false)
    val success: Boolean,

    @Column(nullable = true, columnDefinition = "TEXT")
    val failReason: String?,

) : BaseTimeEntity()