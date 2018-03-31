package com.yunikov.api.test

import com.yunikov.domain.test.TestService
import com.yunikov.api.errors.ErrorHandler
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just
import reactor.core.publisher.toMono


@Component
class TestHandler(private val testService: TestService,
                  private val errorHandler: ErrorHandler) {

    fun test(request: ServerRequest): Mono<ServerResponse> {
        return request.toMono()
                .transform({ testResponse() })
                .onErrorResume({ error -> errorHandler.handle(error) })
    }

    private fun testResponse(): Mono<ServerResponse> {
        return testService.test()
                .flatMap({ just(TestResponse(it)) })
                .flatMap({ ServerResponse.ok()
                        .body(Mono.just(it), TestResponse::class.java)
                })
    }
}