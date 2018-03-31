package com.yunikov.api

import com.yunikov.domain.TestDomainConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
@PropertySource("classpath:test.properties")
@Import(TestDomainConfig::class)
class TestApiApplication