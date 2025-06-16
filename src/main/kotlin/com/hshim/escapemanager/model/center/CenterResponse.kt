package com.hshim.escapemanager.model.center

import com.hshim.escapemanager.database.center.Center

class CenterResponse(
    val id: String,
    val name: String,
    val description: String?,
    val address: String?,
    val phoneNo: String?,
    val email: String?,
    val themeCnt: Int,
) {
    constructor(center: Center): this (
        id = center.id,
        name = center.name,
        description = center.description,
        address = center.address,
        phoneNo = center.phoneNo,
        email = center.email,
        themeCnt = center.themes.size,
    )
}