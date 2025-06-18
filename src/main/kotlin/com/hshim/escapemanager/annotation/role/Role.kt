package com.hshim.escapemanager.annotation.role

import com.hshim.escapemanager.database.account.enums.Role

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Role(
    val roles: Array<Role>
)