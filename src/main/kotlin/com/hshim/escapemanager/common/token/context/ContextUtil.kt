package com.hshim.escapemanager.common.token.context

import com.hshim.escapemanager.exception.GlobalException

object ContextUtil {
    private val threadLocal = ThreadLocal<Context>()

    fun setContext(claims: Map<String, Any>) = setContext(Context(claims))

    fun setContext(context: Context) = threadLocal.set(context)

    fun getContext(): Context? = threadLocal.get()

    fun getAccountId() = getContext()?.accountId ?: throw GlobalException.NOT_FOUND_ACCOUNT_ID.exception

    fun getCenterId() = getContext()?.centerId ?: throw GlobalException.NOT_FOUND_CENTER_ID.exception

    fun getRole() = getContext()?.role ?: throw GlobalException.NOT_FOUND_ROLE.exception

    fun clear() = threadLocal.remove()
}