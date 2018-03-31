package com.yunikov.domain

import com.github.mongobee.Mongobee
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoAuditing
@EnableMongoRepositories
class MongoConfig {

    @Value("\${spring.data.mongodb.uri}")
    private lateinit var mongoUri: String

    @Bean
    fun mongobee(): Mongobee {
        val runner = Mongobee(mongoUri)
        runner.setChangeLogsScanPackage(
                "com.yunikov.domain.migrations")

        return runner
    }
}