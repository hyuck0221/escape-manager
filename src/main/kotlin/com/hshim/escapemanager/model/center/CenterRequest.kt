package com.hshim.escapemanager.model.center

import com.hshim.escapemanager.database.center.Center

class CenterRequest(
    val name: String,
    val description: String?,
    val address: String?,
    val phoneNo: String?,
    val email: String?,
) {
    fun toEntity() = Center(
        name = name,
        description = description,
        address = address,
        phoneNo = phoneNo,
        email = email,
    )

    fun updateTo(center: Center) {
        center.name = name
        center.description = description
        center.address = address
        center.phoneNo = phoneNo
        center.email = email
    }
}