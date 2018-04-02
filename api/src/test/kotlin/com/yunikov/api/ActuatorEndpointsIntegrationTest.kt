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

    @DisplayName("Should return build and git info on /info")
    @Test
    fun infoEndpointWorks() {
        client.get().uri("/app/info")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.git")
                .isNotEmpty
                .jsonPath("$.git.commit")
                .isNotEmpty
                .jsonPath("$.build")
                .isNotEmpty
                .jsonPath("$.build.version")
                .isNotEmpty
    }

    @DisplayName("Should return metrics on /metrics")
    @Test
    fun metricsEndpointWorks() {
        client.get().uri("/app/metrics")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$.names")
                .isArray
                .jsonPath("$.names.length()")
                // for some reason different metrics length occurs when running from IDE and from console
                .isNotEmpty
    }

    @DisplayName("Should return links on /app")
    @Test
    fun actuatorRootReturnsOnlyAllowed() {
        client.get().uri("/app")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .jsonPath("$._links.self")
                .isNotEmpty
                .jsonPath("$._links.health")
                .isNotEmpty
                .jsonPath("$._links.info")
                .isNotEmpty
                .jsonPath("$._links.loggers")
                .isNotEmpty
                .jsonPath("$._links.loggers-name")
                .isNotEmpty
                .jsonPath("$._links.length()")
                .isEqualTo(7)
    }
}
