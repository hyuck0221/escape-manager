package com.hshim.escapemanager.common.token.context

import com.hshim.escapemanager.database.account.enums.Role

class Context(
    val accountId: String,
    val centerId: String,
    val role: Role,
) {
    constructor(claims: Map<String, Any>) : this(
        accountId = claims["accountId"] as String,
        centerId = claims["centerId"] as String,
        role = Role.valueOf((claims["roles"] as List<String>).first()),
    )
}