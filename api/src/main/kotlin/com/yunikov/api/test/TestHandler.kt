package com.yunikov.api.test

import com.yunikov.api.errors.ErrorHandler
import com.yunikov.domain.test.TestService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class TestHandler(private val testService: TestService,
                  private val errorHandler: ErrorHandler) {

    fun test(request: ServerRequest): Mono<ServerResponse> {
        return request.principal()
                .flatMap { principal -> testService.test(principal.name) }
                .map { result -> TestResponse(result) }
                .flatMap { ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .body(Mono.just(it), TestResponse::class.java)
                }
                .onErrorResume({ error -> errorHandler.handleAndRespond(error) })
    }
}