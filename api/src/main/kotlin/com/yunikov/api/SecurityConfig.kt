package com.yunikov.api

import com.yunikov.api.security.JWTAuthenticationConverter
import com.yunikov.api.security.JWTAuthenticationFailureHandler
import com.yunikov.api.security.JWTAuthenticationManager
import com.yunikov.api.security.JWTClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.authentication.AuthenticationWebFilter
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {

    @Value("\${jwt.issuer}")
    private lateinit var jwtIssuer: String

    @Value("\${jwt.secret}")
    private lateinit var jwtSecret: String

    @Autowired
    private lateinit var authenticationManager: JWTAuthenticationManager

    @Autowired
    private lateinit var jwtAuthenticationConverter: JWTAuthenticationConverter

    @Autowired
    private lateinit var jwtAuthenticationFailureHandler: JWTAuthenticationFailureHandler

    @Bean
    fun security(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        return httpSecurity
                .authenticationManager(authenticationManager)
                // disable default security
                    .httpBasic().disable()
                    .formLogin().disable()
                    .logout().disable()
                    .csrf().disable()
                .addFilterAt(apiAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange()
                    .pathMatchers("/app/health").permitAll()
                    .anyExchange()
                    .authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationFailureHandler)
                .and()
                .build()
    }

    @Bean
    fun jwtClient(): JWTClient {
        return JWTClient(jwtIssuer, jwtSecret)
    }

    @Bean
    fun securityContextRepository(): NoOpServerSecurityContextRepository {
        return NoOpServerSecurityContextRepository.getInstance()
    }

    private fun apiAuthenticationWebFilter(): AuthenticationWebFilter {
        val apiAuthenticationWebFilter = AuthenticationWebFilter(authenticationManager)
        apiAuthenticationWebFilter.setAuthenticationFailureHandler(ServerAuthenticationEntryPointFailureHandler(jwtAuthenticationFailureHandler))
        apiAuthenticationWebFilter.setAuthenticationConverter(jwtAuthenticationConverter)
        apiAuthenticationWebFilter.setRequiresAuthenticationMatcher(PathPatternParserServerWebExchangeMatcher("/**"))
        apiAuthenticationWebFilter.setSecurityContextRepository(securityContextRepository())
        return apiAuthenticationWebFilter
    }
}