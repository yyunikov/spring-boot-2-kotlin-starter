package com.yunikov.api

import com.yunikov.api.errors.ErrorHandler
import com.yunikov.api.errors.ErrorResponse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [(TestApiApplication::class)])
@TestPropertySource(locations = ["classpath:application-test.properties"])
@AutoConfigureWebTestClient
@DisplayName("Error handler filter")
class ErrorHandlingIntegrationTest {

    @Autowired
    private lateinit var client: WebTestClient

    @DisplayName("Should return 404 accessing non-existing URL")
    @org.junit.jupiter.api.Test
    fun notFoundIsHandled() {
        val body = client.get().uri("/not_found")
                .exchange()
                .expectStatus().isNotFound
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(ErrorResponse::class.java)
                .returnResult()
                .responseBody

        assertAll({
            assertTrue(body!!.code == ErrorHandler.NOT_FOUND_CODE)
            assertTrue(body.message == HttpStatus.NOT_FOUND.reasonPhrase)
        })
    }
}