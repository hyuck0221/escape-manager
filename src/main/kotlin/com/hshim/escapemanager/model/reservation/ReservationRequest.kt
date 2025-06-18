package com.hshim.escapemanager.model.reservation

import com.hshim.escapemanager.database.theme.Theme
import com.hshim.escapemanager.database.theme.reservation.Reservation
import util.DateUtil.stringToDate

class ReservationRequest(
    val datetime: String,
    val code: String,
    val name: String,
    val phoneNo: String,
) {
    fun toEntity(themeId: String) = Reservation(
        theme = Theme.of(themeId),
        datetime = datetime.stringToDate(),
        code = code,
        name = name,
        phoneNo = phoneNo,
    )

    fun updateTo(reservation: Reservation) {
        reservation.datetime = datetime.stringToDate()
        reservation.code = code
        reservation.name = name
        reservation.phoneNo = phoneNo
    }
}