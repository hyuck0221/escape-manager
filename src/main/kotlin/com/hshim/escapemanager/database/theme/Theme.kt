package com.hshim.escapemanager.database.theme

import com.hshim.escapemanager.database.base.BaseTimeEntity
import com.hshim.escapemanager.database.base.converter.LocalTimesConverter
import com.hshim.escapemanager.database.center.Center
import com.hshim.escapemanager.database.theme.enums.ThemeCategory
import com.hshim.escapemanager.database.theme.reservation.Reservation
import com.hshim.escapemanager.exception.GlobalException
import jakarta.persistence.*
import util.CommonUtil.ulid
import java.time.LocalDate
import java.time.LocalDateTime
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
    var openDays: Int,

    @Column(nullable = false)
    var minPersonCnt: Int,

    @Column(nullable = false)
    var maxPersonCnt: Int,

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Convert(converter = LocalTimesConverter::class)
    var reserveTimes: List<LocalTime>,
) : BaseTimeEntity() {

    val openDateTime: LocalDateTime
        get() = LocalDate.now().plusDays(openDays.toLong()).atTime(reservationOpenTime)

    @OneToMany(
        targetEntity = Reservation::class,
        mappedBy = "theme",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val reservations: MutableSet<Reservation> = mutableSetOf()

    fun reservingTimes(date: LocalDate) = reservations
        .filter { it.datetime.toLocalDate() == date }
        .map { it.datetime.toLocalTime() }

    fun validateReserveTime(datetime: LocalDateTime) {
        when {
            openDateTime == datetime.toLocalDate() && reservationOpenTime > datetime.toLocalTime()
                -> GlobalException.IS_NOT_OPEN_RESERVE_TIME

            !reserveTimes.contains(datetime.toLocalTime())
                -> GlobalException.IS_NOT_RESERVE_TIME

            reservingTimes(datetime.toLocalDate()).contains(datetime.toLocalTime())
                -> GlobalException.IS_ALREADY_RESERVE_TIME

            else -> null
        }?.let { throw it.exception }
    }

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
            openDays = 0,
            minPersonCnt = 0,
            maxPersonCnt = 0,
            reserveTimes = listOf(),
        )
    }
}