package com.hshim.escapemanager.security.component

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.secret}") private val secretKey: String,
    @Value("\${security.jwt.expiry}") private val expiry: Long,
) {
    private val signer = MACSigner(secretKey.toByteArray())

    fun generateToken(username: String, roles: Collection<String>, centerId: String? = null): String {
        val builder = JWTClaimsSet.Builder()
            .subject(username)
            .claim("roles", roles)
            .expirationTime(Date.from(Instant.now().plusSeconds(expiry)))

        if (centerId != null) builder.claim("centerId", centerId)
        return builder.build().let { SignedJWT(JWSHeader(JWSAlgorithm.HS256), it).apply { sign(signer) }.serialize() }
    }

    fun getAuthentication(token: String): Authentication? {
        return try {
            val claims = SignedJWT.parse(token).jwtClaimsSet
            if (claims.expirationTime.before(Date())) return null
            val authorities = (claims.getStringListClaim("roles") ?: listOf())
                .map { SimpleGrantedAuthority("ROLE_$it") }
            UsernamePasswordAuthenticationToken(claims.subject, null, authorities)
        } catch (ex: Exception) {
            null
        }
    }
}