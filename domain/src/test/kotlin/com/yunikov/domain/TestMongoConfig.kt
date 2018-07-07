package com.yunikov.domain

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableReactiveMongoRepositories
class TestMongoConfig {

    companion object {
        const val mongoDbName: String = "spring-boot-2-kotlin-starter"
    }

    @Bean
    fun reactiveMongoClient(): MongoClient {
        return MongoClients.create()
    }

    @Bean
    fun reactiveMongoTemplate(mongoClient: MongoClient): ReactiveMongoTemplate {
        return ReactiveMongoTemplate(mongoClient, mongoDbName)
    }
}