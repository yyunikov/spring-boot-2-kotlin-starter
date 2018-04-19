package com.yunikov.api

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [TestApiApplication::class])
@TestPropertySource(locations = ["classpath:application-test.properties"])
@AutoConfigureWebTestClient
@DisplayName("Actuator endpoints")
class ActuatorEndpointsIntegrationTest {

    @Autowired
    private lateinit var client: WebTestClient

    @DisplayName("Should return status on /health")
    @Test
    fun healthEndpointWorks() {
        client.get().uri("/app/health")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody().json("{\"status\": \"UP\"}")
    }
}
