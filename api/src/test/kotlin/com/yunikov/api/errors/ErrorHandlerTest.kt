package com.yunikov.api.errors

import com.yunikov.api.errors.ErrorHandler
import com.yunikov.api.errors.ErrorResponse
import com.yunikov.api.errors.exceptions.ResourceNotFoundException
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.EntityResponse
import reactor.core.publisher.Mono

class ErrorHandlerTest {

    private lateinit var errorHandler: ErrorHandler

    @Before
    fun setUp() {
        errorHandler = ErrorHandler()
    }

    @Test
    fun notFoundIsHandled() {
        assertErrorIsHandled(ResourceNotFoundException(""),
                HttpStatus.NOT_FOUND,
                ErrorHandler.NOT_FOUND_CODE,
                HttpStatus.NOT_FOUND.reasonPhrase)
    }

    @Test
    fun internalErrorIsHandled() {
        assertErrorIsHandled(IllegalStateException(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorHandler.INTERNAL_ERROR_CODE,
                HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
    }

    private fun assertErrorIsHandled(throwable: Throwable,
                                     expectedHttpStatus: HttpStatus,
                                     expectedCode:Int,
                                     expectedMessage:String) {
        errorHandler.handle(throwable)
                .subscribe({ response ->
                    @Suppress("UNCHECKED_CAST")
                    val entityResponse = response as EntityResponse<Mono<ErrorResponse>>
                    assertEquals(expectedHttpStatus, response.statusCode())

                    entityResponse.entity().subscribe({ errorResponse ->
                        assertEquals(expectedCode, errorResponse.code)
                        assertEquals(expectedMessage, errorResponse.message)
                    })
                })
    }
}