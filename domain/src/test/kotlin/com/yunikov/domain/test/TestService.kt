package com.yunikov.domain.test

import reactor.core.publisher.Mono

interface TestService {
    fun test(): Mono<String>
}