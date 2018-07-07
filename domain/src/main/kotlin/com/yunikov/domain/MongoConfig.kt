package com.yunikov.domain

import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableMongoAuditing
@EnableReactiveMongoRepositories
class MongoConfig {

//
//    TODO re-enable after fixing system.indexes call from MongoBee
//    https://github.com/mongobee/mongobee/pull/87
//    https://stackoverflow.com/questions/49958635/mongodb-atlas-user-is-not-allowed-to-do-action-find-on-system-indexes
//    @Value("\${spring.data.mongodb.uri}")
//    private lateinit var mongoUri: String
//
//    @Bean
//    fun mongobee(): Mongobee {
//        val runner = Mongobee(mongoUri)
//        runner.setChangeLogsScanPackage(
//                "com.yunikov.domain.migrations")
//
//        return runner
//    }
}