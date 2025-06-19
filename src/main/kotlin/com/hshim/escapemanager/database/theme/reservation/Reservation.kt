package com.hshim.escapemanager.database.theme.reservation

import com.hshim.escapemanager.common.token.context.ContextUtil.getAccountId
import com.hshim.escapemanager.common.token.context.ContextUtil.getContext
import com.hshim.escapemanager.common.token.context.ContextUtil.getRole
import com.hshim.escapemanager.database.account.enums.Role
import com.hshim.escapemanager.database.account.user.User
import com.hshim.escapemanager.database.base.BaseTimeEntity
import com.hshim.escapemanager.database.theme.Theme
import com.hshim.escapemanager.exception.GlobalException
import jakarta.persistence.*
import util.CommonUtil.ulid
import util.DateUtil.dateToString
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "reservation")
class Reservation(
    @Id
    @Column(columnDefinition = "varchar(36)")
    val id: String = ulid(),

    @ManyToOne(targetEntity = Theme::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "theme_id", nullable = false, columnDefinition = "varchar(36)")
    val theme: Theme,

    @ManyToOne(targetEntity = User::class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true, columnDefinition = "varchar(36)")
    val user: User?,

    @Column(nullable = false)
    var datetime: LocalDateTime,

    @Column(nullable = false)
    var code: String = buildCode(),

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var phoneNo: String,

) : BaseTimeEntity() {

    @OneToMany(targetEntity = ReservationLog::class, mappedBy = "reservation", cascade = [CascadeType.ALL])
    val reservationLogs: MutableSet<ReservationLog> = mutableSetOf()

    fun validateUser() {
        when {
            user == null -> null
            getContext() != null && getRole() != Role.USER -> null
            getContext() == null -> GlobalException.ACCOUNT_FORBIDDEN
            getAccountId() != user?.id -> GlobalException.ACCOUNT_FORBIDDEN
            else -> null
        }?.let { throw it.exception }
    }

    fun toLogEntity(taskId: String, exception: Exception?) = ReservationLog(
        taskId = taskId,
        theme = theme,
        user = user,
        reservation = if (exception == null) this else null,
        success = exception == null,
        failReason = exception?.message
    )

    companion object {
        fun buildCode(): String {
            return LocalDateTime.now().dateToString("yyyyMMddHHmmss") + String.format("%04d", Random().nextInt(10000))
        }
    }
}