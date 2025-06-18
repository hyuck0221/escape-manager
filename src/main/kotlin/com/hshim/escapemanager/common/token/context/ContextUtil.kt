package com.hshim.escapemanager.common.token.context

import com.hshim.escapemanager.exception.GlobalException

object ContextUtil {
    private val threadLocal = ThreadLocal<Context>()

    fun set(claims: Map<String, Any>) = set(Context(claims))

    fun set(context: Context) = threadLocal.set(context)

    fun get(): Context? = threadLocal.get()

    fun getAccountId() = get()?.accountId ?: throw GlobalException.NOT_FOUND_ACCOUNT_ID.exception

    fun getCenterId() = get()?.centerId ?: throw GlobalException.NOT_FOUND_CENTER_ID.exception

    fun getRole() = get()?.role ?: throw GlobalException.NOT_FOUND_ROLE.exception

    fun clear() = threadLocal.remove()
}