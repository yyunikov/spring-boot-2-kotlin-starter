package com.yunikov.api

import com.yunikov.api.errors.ErrorHandler
import com.yunikov.api.test.TestHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RequestPredicates.GET
import org.springframework.web.reactive.function.server.RequestPredicates.accept
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.nest
import org.springframework.web.reactive.function.server.RouterFunctions.route

open class WebConfig: WebFluxConfigurer {

    @Bean
    fun apiRouter(testHandler: TestHandler,
                  errorHandler: ErrorHandler): RouterFunction<*> {
        return nest(accept(MediaType.APPLICATION_JSON_UTF8),
                route(GET(TEST_PATH), HandlerFunction { request -> testHandler.test(request) })
        ).andOther(route(RequestPredicates.all(), HandlerFunction { errorHandler.notFound() }))
    }

    companion object {
        const val TEST_PATH = "/test"
    }
}