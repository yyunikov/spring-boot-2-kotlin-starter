package com.yunikov.domain

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Import

@ComponentScan(basePackages = ["com.yunikov.domain"])
@Import(TestMongoConfig::class)
class TestDomainConfig