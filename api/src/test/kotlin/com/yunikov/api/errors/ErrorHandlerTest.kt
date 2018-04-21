package com.yunikov.api.errors

import com.yunikov.api.errors.exceptions.ResourceNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.EntityResponse
import reactor.core.publisher.Mono

@DisplayName("Error handler")
class ErrorHandlerTest {

    private val errorHandler: ErrorHandler = ErrorHandler()

    @Test
    fun `Should handle not found error`() {
        assertErrorIsHandled(ResourceNotFoundException(""),
                HttpStatus.NOT_FOUND,
                ErrorHandler.NOT_FOUND_CODE,
                HttpStatus.NOT_FOUND.reasonPhrase)
    }

    @Test
    fun `Should handle internal server error`() {
        assertErrorIsHandled(IllegalStateException(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorHandler.INTERNAL_ERROR_CODE,
                HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase)
    }

    private fun assertErrorIsHandled(throwable: Throwable,
                                     expectedHttpStatus: HttpStatus,
                                     expectedCode:Int,
                                     expectedMessage:String) {
        errorHandler.handleAndRespond(throwable)
                .subscribe({ response ->
                    @Suppress("UNCHECKED_CAST")
                    val entityResponse = response as EntityResponse<Mono<ErrorResponse>>
                    assertEquals(expectedHttpStatus, response.statusCode())

                    entityResponse.entity().subscribe({ errorResponse ->
                        assertAll({
                            assertEquals(expectedCode, errorResponse.code)
                            assertEquals(expectedMessage, errorResponse.message)
                        })
                    })
                })
    }
}