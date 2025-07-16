package com.squrlabs.sca.config.auth.tokenandcookie

import com.squrlabs.sca.config.AppProperties
import com.squrlabs.sca.config.auth.util.UserPrincipal
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import java.util.*
import javax.crypto.SecretKey
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class TokenProvider(private val appProperties: AppProperties) {

  fun createToken(authentication: Authentication): String {
    val userPrincipal: UserPrincipal = authentication.principal as UserPrincipal
    return generateToken(userPrincipal)
  }

  fun generateToken(userPrincipal: UserPrincipal): String {
    val now = Date()
    val expiryDate = Date(now.time + appProperties.auth.tokenExpirationMsec)

    return Jwts.builder()
        .subject(userPrincipal.id)
        .issuedAt(Date())
        .expiration(expiryDate)
        .signWith(getSigningKey())
        .compact()
  }

  fun getUserIdFromToken(token: String?): String {
    val claims: Claims =
        Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload()
    return claims.subject
  }

  fun validateToken(authToken: String?): Boolean {
    return try {
      Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken)
      true
    } catch (ex: Exception) {
      false
    }
  }

  private fun getSigningKey(): SecretKey {
    val keyBytes = appProperties.auth.tokenSecret?.toByteArray()
    return Keys.hmacShaKeyFor(keyBytes)
  }
}
