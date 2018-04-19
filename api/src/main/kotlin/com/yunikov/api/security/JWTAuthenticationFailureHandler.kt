package com.yunikov.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.yunikov.api.errors.ErrorHandler
import com.yunikov.api.errors.HttpErrorHandler
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JWTAuthenticationFailureHandler(objectMapper: ObjectMapper): HttpErrorHandler(objectMapper), ServerAuthenticationEntryPoint {

    override fun commence(serverWebExchange: ServerWebExchange, e: AuthenticationException): Mono<Void> {
        return handle(serverWebExchange, ErrorHandler.UNAUTHORIZED_CODE, HttpStatus.UNAUTHORIZED)
    }
}