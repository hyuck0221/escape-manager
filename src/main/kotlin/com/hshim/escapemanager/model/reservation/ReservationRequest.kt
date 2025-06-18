package com.hshim.escapemanager.model.reservation

import com.hshim.escapemanager.database.account.user.User
import com.hshim.escapemanager.database.theme.Theme
import com.hshim.escapemanager.database.theme.reservation.Reservation
import util.DateUtil.stringToDate

class ReservationRequest(
    val datetime: String,
    val name: String,
    val phoneNo: String,
) {
    fun toEntity(theme: Theme, userId: String?) = Reservation(
        theme = theme,
        user = userId?.let { User.of(it) },
        datetime = datetime.stringToDate("yyyy-MM-dd HH:mm"),
        name = name,
        phoneNo = phoneNo,
    )
}