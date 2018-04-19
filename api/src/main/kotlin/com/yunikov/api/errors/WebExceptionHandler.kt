package com.yunikov.api.errors

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class WebExceptionHandler(private val errorHandler: ErrorHandler, objectMapper: ObjectMapper):
        HttpErrorHandler(objectMapper), ErrorWebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        return errorHandler.handle(ex).flatMap { errorResponse -> handle(exchange, errorResponse) }
    }
}