package com.yunikov.api.errors

import com.yunikov.api.errors.exceptions.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

@Component
class ErrorHandler {

    fun handle(error: Throwable): Mono<ServerResponse> {
        when(error) {
            is ResourceNotFoundException -> return notFound()
        }

        return internalError(error)
    }

    fun notFound(): Mono<ServerResponse> {
        return toResponse(HttpStatus.NOT_FOUND,
                just(ErrorResponse(NOT_FOUND_CODE, HttpStatus.NOT_FOUND.reasonPhrase)))
    }

    private fun internalError(error: Throwable): Mono<ServerResponse> {
        log.error(error.message, error)
        return toResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                just(ErrorResponse(INTERNAL_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)))
    }

    private fun toResponse(httpStatus: HttpStatus, monoError: Mono<ErrorResponse>): Mono<ServerResponse> {
        return monoError
                .flatMap({errorResponse ->
                    ServerResponse
                            .status(httpStatus)
                            .body(just(errorResponse), ErrorResponse::class.java)
                })
    }

    companion object {
        private val log = LoggerFactory.getLogger(ErrorHandler::class.java)

        const val INTERNAL_ERROR_CODE = 1000
        const val NOT_FOUND_CODE = 1001
    }
}