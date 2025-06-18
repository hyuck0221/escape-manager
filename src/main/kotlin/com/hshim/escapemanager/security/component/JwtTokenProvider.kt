package com.hshim.escapemanager.security.component

import com.hshim.escapemanager.database.account.Account
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

    fun generateToken(
        account: Account,
        roles: Collection<String>,
    ): String {
        val claimsBuilder = JWTClaimsSet.Builder()
            .subject(account.loginId)
            .claim("roles", roles)
            .claim("accountId", account.id)
            .expirationTime(Date.from(Instant.now().plusSeconds(expiry)))

        account.centerId?.let { claimsBuilder.claim("centerId", it) }

        val signedJwt = SignedJWT(JWSHeader(JWSAlgorithm.HS256), claimsBuilder.build())
        signedJwt.sign(signer)
        return signedJwt.serialize()
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

    fun getClaims(token: String): Map<String, Any>? {
        return try {
            val claims = SignedJWT.parse(token).jwtClaimsSet
            if (claims.expirationTime.before(Date())) return null
            claims.claims
        } catch (ex: Exception) {
            null
        }
    }
}