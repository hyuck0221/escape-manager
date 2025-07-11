package com.hshim.escapemanager.model.theme

import com.hshim.escapemanager.database.theme.Theme
import com.hshim.escapemanager.database.theme.enums.ThemeCategory
import util.DateUtil.dateToString

class ThemeResponse(
    val id: String,
    val centerId: String,
    val name: String,
    val description: String?,
    val category: ThemeCategory?,
    val difficulty: Double?,
    val fear: Double?,
    val activity: Double?,
    val reservationOpenTime: String,
    val minPersonCnt: Int,
    val maxPersonCnt: Int,
    val reserveTimes: List<String>,
) {
    constructor(theme: Theme) : this(
        id = theme.id,
        centerId = theme.center!!.id,
        name = theme.name,
        description = theme.description,
        category = theme.category,
        difficulty = theme.difficulty,
        fear = theme.fear,
        activity = theme.activity,
        reservationOpenTime = theme.reservationOpenTime.dateToString("HH:mm"),
        minPersonCnt = theme.minPersonCnt,
        maxPersonCnt = theme.maxPersonCnt,
        reserveTimes = theme.reserveTimes.map { it.dateToString("HH:mm") },
    )
}