package com.squrlabs.sca.config

import com.squrlabs.sca.config.auth.tokenandcookie.CustomChannelInterceptor
import com.squrlabs.sca.config.auth.tokenandcookie.TokenProvider
import com.squrlabs.sca.domain.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    @Autowired val tokenProvider: TokenProvider,
    @Autowired val userService: UserService,
    private val appProperties: AppProperties
) : WebSocketMessageBrokerConfigurer {

  override fun registerStompEndpoints(registry: StompEndpointRegistry) {
    registry.addEndpoint("/ws").setAllowedOrigins(appProperties.cors.allowedOrigin).withSockJS()
  }

  override fun configureMessageBroker(config: MessageBrokerRegistry) {
    config.enableSimpleBroker("/notifications")
    config.setApplicationDestinationPrefixes("/app")
  }

  override fun configureClientInboundChannel(registration: ChannelRegistration) {
    registration.interceptors(CustomChannelInterceptor(tokenProvider, userService))
  }
}
