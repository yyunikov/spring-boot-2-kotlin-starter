package com.yunikov.api

import com.yunikov.domain.DomainConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@EnableWebFlux
//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
@Import(WebConfig::class, DomainConfig::class)
class ApiApplication

fun main(args: Array<String>) {
    SpringApplication.run(ApiApplication::class.java, *args)
}
