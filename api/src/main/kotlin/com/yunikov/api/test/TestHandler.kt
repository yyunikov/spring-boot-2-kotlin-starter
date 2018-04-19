package com.yunikov.api.test

import com.yunikov.api.errors.ErrorHandler
import com.yunikov.domain.test.TestService
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
        // TODO why request.principal() and ReactiveSecurityContextHolder.getContext().block() are always null?
        // https://github.com/spring-projects/spring-security/issues/5248
        // https://stackoverflow.com/questions/49927500/spring-security-principal-is-always-null-using-routerfunction
        return request.toMono()
                .transform({ testResponse() })
                .onErrorResume({ error -> errorHandler.handleAndRespond(error) })
    }

    private fun testResponse(): Mono<ServerResponse> {
        return testService.test()
                .flatMap({ just(TestResponse(it)) })
                .flatMap({ ServerResponse.ok()
                        .body(Mono.just(it), TestResponse::class.java)
                })
    }
}