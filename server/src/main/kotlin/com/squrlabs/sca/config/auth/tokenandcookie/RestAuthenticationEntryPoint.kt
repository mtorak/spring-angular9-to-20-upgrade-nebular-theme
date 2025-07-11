package com.squrlabs.sca.config.auth.tokenandcookie

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class RestAuthenticationEntryPoint : AuthenticationEntryPoint {

  override fun commence(
      request: HttpServletRequest?,
      response: HttpServletResponse?,
      authException: AuthenticationException?
  ) {
    response!!.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException!!.localizedMessage)
  }
}
