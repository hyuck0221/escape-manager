package com.hshim.escapemanager.model.account.admin

import com.hshim.escapemanager.database.account.admin.Admin

class AdminResponse(
    val id: String,
    val name: String,
    val description: String?,
) {
    constructor(admin: Admin): this (
        id = admin.id,
        name = admin.name,
        description = admin.description,
    )
}