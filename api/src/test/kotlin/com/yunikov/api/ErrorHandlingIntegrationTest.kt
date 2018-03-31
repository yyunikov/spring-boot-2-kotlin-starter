package com.yunikov.api

import com.yunikov.api.errors.ErrorHandler
import com.yunikov.api.errors.ErrorResponse
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient


@RunWith(SpringRunner::class)
@SpringBootTest(classes = [(TestApiApplication::class)])
@TestPropertySource(locations = ["classpath:application-test.properties"])
@AutoConfigureWebTestClient
class ErrorHandlingIntegrationTest {

    @Autowired
    private lateinit var client: WebTestClient

    @Test
    fun notFoundIsHandled() {
        val body = client.get().uri("/not_found")
                .exchange()
                .expectStatus().isNotFound
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(ErrorResponse::class.java)
                .returnResult()
                .responseBody

        Assert.assertTrue(body!!.code == ErrorHandler.NOT_FOUND_CODE)
        Assert.assertTrue(body.message == HttpStatus.NOT_FOUND.reasonPhrase)
    }
}