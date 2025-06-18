package com.hshim.escapemanager.model.account.user

import com.hshim.escapemanager.database.account.user.User

class UserResponse(
    val id: String,
    val name: String,
    val description: String?,
) {
    constructor(user: User) : this(
        id = user.id,
        name = user.name,
        description = user.description,
    )
}