package com.hshim.escapemanager.model.reservation

import com.hshim.escapemanager.database.theme.reservation.Reservation
import com.hshim.escapemanager.model.account.user.UserResponse
import util.DateUtil.dateToString

class ReservationResponse(
    val id: String,
    val themeId: String,
    val datetime: String,
    var user: UserResponse?,
    var code: String?,
    var name: String?,
    var phoneNo: String?,
) {
    private constructor(reservation: Reservation) : this(
        id = reservation.id,
        themeId = reservation.theme.id,
        datetime = reservation.datetime.dateToString("yyyy-MM-dd HH:mm"),
        user = null,
        code = null,
        name = null,
        phoneNo = null,
    )

    companion object {
        fun simple(reservation: Reservation) = ReservationResponse(reservation)
        fun detail(reservation: Reservation) = ReservationResponse(reservation)
            .apply {
                this.user = reservation.user?.let { UserResponse(it) }
                this.code = reservation.code
                this.name = reservation.name
                this.phoneNo = reservation.phoneNo
            }
    }
}