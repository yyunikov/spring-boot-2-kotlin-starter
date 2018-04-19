package com.yunikov.api.errors

import com.yunikov.api.errors.exceptions.ResourceNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

@Component
class ErrorHandler {

    fun handle(error: Throwable): Mono<ErrorResponse> {
        when(error) {
            is ResourceNotFoundException -> return notFound()
            is AccessDeniedException -> return forbidden()
            is BadCredentialsException -> return unauthorized()
        }

        return internalError(error)
    }

    fun handleAndRespond(error: Throwable): Mono<ServerResponse> {
        when(error) {
            is ResourceNotFoundException -> return toResponse(HttpStatus.NOT_FOUND, notFound())
            is AccessDeniedException -> return toResponse(HttpStatus.FORBIDDEN, forbidden())
            is BadCredentialsException -> return toResponse(HttpStatus.UNAUTHORIZED, unauthorized())
        }

        log.error(error.message, error)
        return toResponse(HttpStatus.INTERNAL_SERVER_ERROR, internalError(error))
    }

    fun notFound(): Mono<ErrorResponse> {
        return just(ErrorResponse(NOT_FOUND_CODE, HttpStatus.NOT_FOUND.reasonPhrase, HttpStatus.NOT_FOUND))
    }

    fun notFoundResponse(): Mono<ServerResponse> {
        return toResponse(HttpStatus.NOT_FOUND, just(ErrorResponse(NOT_FOUND_CODE, HttpStatus.NOT_FOUND.reasonPhrase, HttpStatus.NOT_FOUND)))
    }

    fun forbidden(): Mono<ErrorResponse> {
        return just(ErrorResponse(FORBIDDEN_CODE, "Access Denied", HttpStatus.FORBIDDEN))
    }

    fun unauthorized(): Mono<ErrorResponse> {
        return just(ErrorResponse(UNAUTHORIZED_CODE, HttpStatus.UNAUTHORIZED.reasonPhrase, HttpStatus.UNAUTHORIZED))
    }

    private fun internalError(error: Throwable): Mono<ErrorResponse> {
        log.error(error.message, error)
        return just(ErrorResponse(INTERNAL_ERROR_CODE, HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, HttpStatus.INTERNAL_SERVER_ERROR))
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
        const val UNAUTHORIZED_CODE = 1002
        const val FORBIDDEN_CODE = 1003
    }
}