package com.hshim.escapemanager.model.reservation

import com.hshim.escapemanager.database.theme.reservation.Reservation
import util.DateUtil.dateToString

class ReservationResponse(
    val id: String,
    val themeId: String,
    val datetime: String,
    val code: String,
    val name: String,
    val phoneNo: String,
) {
    constructor(reservation: Reservation) : this(
        id = reservation.id,
        themeId = reservation.theme.id,
        datetime = reservation.datetime.dateToString(),
        code = reservation.code,
        name = reservation.name,
        phoneNo = reservation.phoneNo,
    )
}