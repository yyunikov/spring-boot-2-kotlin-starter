package com.yunikov.api.errors

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.nio.charset.Charset

abstract class HttpErrorHandler(private val objectMapper: ObjectMapper) {

    fun handle(serverWebExchange: ServerWebExchange, errorCode: Int, httpStatus: HttpStatus): Mono<Void> {
        return handle(serverWebExchange, ErrorResponse(errorCode, httpStatus.reasonPhrase, httpStatus))
    }

    fun handle(exchange: ServerWebExchange, errorResponse: ErrorResponse): Mono<Void> {
        val response = exchange.response
        response.headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
        response.statusCode = errorResponse.httpStatus

        val errorJson = objectMapper.writeValueAsString(errorResponse)
        val dataBufferFactory = response.bufferFactory()
        val buffer = dataBufferFactory.wrap(errorJson.toByteArray(Charset.defaultCharset()))

        return response.writeWith(Mono.just(buffer)).doOnError { DataBufferUtils.release(buffer) }
    }
}