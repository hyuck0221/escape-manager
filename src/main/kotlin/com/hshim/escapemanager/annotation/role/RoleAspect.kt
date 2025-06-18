package com.hshim.escapemanager.annotation.role

import com.hshim.escapemanager.common.CommonUtil.getAnnotation
import com.hshim.escapemanager.common.token.context.ContextUtil.getAccountId
import com.hshim.escapemanager.database.account.repository.AccountRepository
import com.hshim.escapemanager.exception.GlobalException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class RoleAspect(private val accountRepository: AccountRepository) {
    @Around("@annotation(com.hshim.escapemanager.annotation.role.Role)")
    fun validateSuperAdmin(joinPoint: ProceedingJoinPoint): Any? {
        val annotation = joinPoint.getAnnotation(Role::class.java)
        if (!accountRepository.existsByIdAndRoleIn(getAccountId(), annotation.roles.toList()))
            throw GlobalException.ACCOUNT_FORBIDDEN.exception
        return joinPoint.proceed()
    }
}