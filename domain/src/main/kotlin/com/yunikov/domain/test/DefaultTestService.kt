package com.yunikov.domain.test

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

@Service
class DefaultTestService: TestService {

    override fun test(): Mono<String> {
        return just("Test")
    }
}