package com.hshim.escapemanager.database.reservation

import com.hshim.escapemanager.database.base.BaseTimeEntity
import com.hshim.escapemanager.database.theme.Theme
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

    @Column(nullable = false)
    var datetime: LocalDateTime,

    @Column(nullable = false)
    var code: String = buildCode(),

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var phoneNo: String,

    ) : BaseTimeEntity() {

    companion object {
        fun buildCode(): String {
            return LocalDateTime.now().dateToString("yyyyMMddHHmmss") + String.format("%04d", Random().nextInt(10000))
        }
    }
}