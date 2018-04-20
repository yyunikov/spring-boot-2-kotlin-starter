package com.yunikov.domain.test

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Mono.just

@Service
class DefaultTestService: TestService {

    override fun test(message: String): Mono<String> {
        return just(message)
    }
}