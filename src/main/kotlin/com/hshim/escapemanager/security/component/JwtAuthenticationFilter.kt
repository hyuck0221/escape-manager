package com.hshim.escapemanager.security.component

import com.hshim.escapemanager.common.token.context.ContextUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val tokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        try {
            val header = request.getHeader(HttpHeaders.AUTHORIZATION)
            if (header?.startsWith("Bearer ") == true) {
                val token = header.substring(7)
                tokenProvider.getAuthentication(token)?.let { SecurityContextHolder.getContext().authentication = it }
                tokenProvider.getClaims(token)?.let { ContextUtil.setContext(it) }
            }
            chain.doFilter(request, response)
        } finally {
            ContextUtil.clear()
        }
    }
}