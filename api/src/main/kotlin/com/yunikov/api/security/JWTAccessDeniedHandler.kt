package com.yunikov.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.yunikov.api.errors.ErrorHandler
import com.yunikov.api.errors.HttpErrorHandler
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

/**
 * Access denied handler implementation. Unfortunately is not used because ServerHttpSecurity doesn't set implementation,
 * initializes ExceptionTranslationWebFilter via new operator and has hardcoded HttpBasicServerAuthenticationEntryPoint.
 */
@Component
class JWTAccessDeniedHandler(objectMapper: ObjectMapper) : HttpErrorHandler(objectMapper), ServerAccessDeniedHandler {

    override fun handle(exchange: ServerWebExchange, e: AccessDeniedException): Mono<Void> {
        return handle(exchange, ErrorHandler.FORBIDDEN_CODE, HttpStatus.FORBIDDEN)
    }
}