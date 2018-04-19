package com.yunikov.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.yunikov.api.errors.ErrorHandler
import com.yunikov.api.test.TestHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.router

@EnableWebFlux
open class WebConfig: WebFluxConfigurer {

    @Bean
    fun apiRouter(testHandler: TestHandler,
                  errorHandler: ErrorHandler): RouterFunction<*> = router {
        (accept(MediaType.APPLICATION_JSON_UTF8).nest {
            GET(TEST_PATH, { request -> testHandler.test(request) }  )

            // nest paths like:
            //"/test2".nest { GET(TEST_PATH, { request -> testHandler.test(request) }) }
        })
    }.andOther(route(RequestPredicates.all(), HandlerFunction { errorHandler.notFoundResponse() }))

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    companion object {
        const val TEST_PATH = "/test"
    }
}