package com.hshim.escapemanager.model.theme

import com.hshim.escapemanager.database.center.Center
import com.hshim.escapemanager.database.theme.Theme
import com.hshim.escapemanager.database.theme.enums.ThemeCategory
import util.DateUtil.stringToDate

class ThemeRequest(
    val name: String,
    val description: String?,
    val category: ThemeCategory?,
    val difficulty: Double?,
    val fear: Double?,
    val activity: Double?,
    val reservationOpenTime: String,
    val openDays: Int,
    val minPersonCnt: Int,
    val maxPersonCnt: Int,
    val reserveTimes: List<String>,
) {
    fun toEntity(centerId: String) = Theme(
        name = name,
        center = Center.of(centerId),
        description = description,
        category = category,
        difficulty = difficulty,
        fear = fear,
        activity = activity,
        reservationOpenTime = reservationOpenTime.stringToDate(),
        openDays = openDays,
        minPersonCnt = minPersonCnt,
        maxPersonCnt = maxPersonCnt,
        reserveTimes = reserveTimes.map { it.stringToDate("HH:mm") },
    )

    fun updateTo(theme: Theme) {
        theme.name = name
        theme.description = description
        theme.category = category
        theme.difficulty = difficulty
        theme.fear = fear
        theme.activity = activity
        theme.reservationOpenTime = reservationOpenTime.stringToDate()
        theme.openDays = openDays
        theme.minPersonCnt = minPersonCnt
        theme.maxPersonCnt = maxPersonCnt
    }
}