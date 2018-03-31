package com.yunikov.domain

import org.springframework.context.annotation.Import
import org.springframework.context.annotation.PropertySource

@PropertySource("classpath:test.properties")
@Import(TestMongoConfig::class)
class TestRepositoryConfig