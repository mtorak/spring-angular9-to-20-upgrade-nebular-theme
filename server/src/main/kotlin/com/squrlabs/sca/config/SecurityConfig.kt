package com.squrlabs.sca.config

import com.squrlabs.sca.config.auth.service.CustomOAuth2UserService
import com.squrlabs.sca.config.auth.tokenandcookie.*
import com.squrlabs.sca.domain.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.BeanIds
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfig(
    @Autowired val userService: UserService,
    @Autowired val tokenAuthenticationFilter: TokenAuthenticationFilter,
    @Autowired val authEntryPoint: RestAuthenticationEntryPoint,
    @Autowired val customOAuth2UserService: CustomOAuth2UserService,
    @Autowired val oAuth2SuccessHandler: OAuth2SuccessHandler,
    @Autowired val oAuth2RequestRepository: OAuth2RequestRepository,
    @Autowired val oAuth2FailureHandler: OAuth2FailureHandler,
    private val appProperties: AppProperties,
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Throws(Exception::class)
    fun authenticationManagerBean(config: AuthenticationConfiguration): AuthenticationManager {
        return config.authenticationManager
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider(userService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain {

        http
            .csrf { it.disable() }
            .cors { cors -> cors.configurationSource(corsConfigurationSource()) }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .exceptionHandling { it.authenticationEntryPoint(authEntryPoint) }
            .authorizeHttpRequests {
                it.requestMatchers(
                        "/",
                        "/error",
                        "/favicon.ico",
                        "/**.png",
                        "/**.gif",
                        "/**.svg",
                        "/**.jpg",
                        "/**.jpeg",
                        "/**.html",
                        "/**.css",
                        "/**.js",
                        "/uploads/**",
                    )
                    .permitAll()
                    .requestMatchers(
                        "/api/account/**",
                        "/api/docs",
                        "/login/oauth2/code/**",
                        "/ws/**",
                    )
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .oauth2Login { oauth2Login ->
                oauth2Login.authorizationEndpoint {
                    it.authorizationRequestRepository(oAuth2RequestRepository)
                }
                oauth2Login.userInfoEndpoint { it.userService(customOAuth2UserService) }
                oauth2Login
                    .successHandler(oAuth2SuccessHandler)
                    .failureHandler(oAuth2FailureHandler)
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .addFilterBefore(
                tokenAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java,
            )

        return http.build()
    }

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {

        val corsConfiguration = CorsConfiguration()
        corsConfiguration.setAllowedOrigins(
            mutableListOf<String?>(appProperties.cors.allowedOrigin)
        )
        corsConfiguration.setAllowedMethods(
            mutableListOf<String?>("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        )
        corsConfiguration.setAllowCredentials(true)
        corsConfiguration.setAllowedHeaders(mutableListOf<String?>("*"))
        corsConfiguration.setMaxAge(MAX_AGE_SECS)

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }

    companion object {
        const val MAX_AGE_SECS: Long = 3600
    }
}
