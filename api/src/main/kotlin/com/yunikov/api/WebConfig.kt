package com.yunikov.api

import com.yunikov.api.errors.ErrorHandler
import com.yunikov.api.test.TestHandler
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.config.WebFluxConfigurer
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.router

open class WebConfig: WebFluxConfigurer {

    @Bean
    fun apiRouter(testHandler: TestHandler,
                  errorHandler: ErrorHandler): RouterFunction<*> = router {
        (accept(MediaType.APPLICATION_JSON_UTF8).nest {
            GET(TEST_PATH, testHandler::test )

            // nest paths like:
            //"/test2".nest {
            // GET(TEST2_PATH, { request -> testHandler.test(request)
            // POST(TEST2_PATH, { request -> testHandler.test(request)
            // }) }
        })
    }.andOther(route(RequestPredicates.all(), HandlerFunction { errorHandler.notFound() }))

    companion object {
        const val TEST_PATH = "/test"
    }
}