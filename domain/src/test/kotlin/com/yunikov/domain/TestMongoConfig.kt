package com.yunikov.domain

import com.mongodb.MongoClient
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories
class TestMongoConfig {

    companion object {
        private val log = LoggerFactory.getLogger(TestMongoConfig::class.java)
        const val mongoDbName: String = "test"
        const val mongoBindIp: String = "127.0.0.1"
    }

    @Value("\${test.mongodb.port}")
    private var mongoPort: String = ""

    @Bean
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun mongoClient(): MongoClient {
        val mongo = EmbeddedMongoFactoryBean()
        mongo.setBindIp(mongoBindIp)
        mongo.setPort(mongoPort.toInt())
        return mongo.`object`
    }

    @Bean
    @Throws(Exception::class)
    fun mongoTemplate(mongoClient: MongoClient): MongoTemplate {
//        try {
//            mongoImport(mongoClient, "collection_name", data.file.absolutePath)
//        } catch (e: Exception) {
//            log.error("Failed to import test data", e)
//        }

        return MongoTemplate(mongoClient, mongoDbName)
    }

//    fun mongoImport(mongoClient: MongoClient, collection: String, jsonFile: String) {
//        val mongoImport = startMongoImport(mongoBindIp, mongoPort.toInt(), mongoDbName, collection, jsonFile)
//        try {
//            log.info("Performed mongoimport of test data. Test db names: " + mongoClient.listDatabaseNames())
//        } finally {
//            mongoImport.stop()
//        }
//    }
//
//    @Throws(UnknownHostException::class, IOException::class)
//    private fun startMongoImport(bindIp: String,
//                                 port: Int,
//                                 dbName: String,
//                                 collection: String,
//                                 jsonFile: String): MongoImportProcess {
//        val mongoImportConfig = MongoImportConfigBuilder()
//                .version(Version.Main.PRODUCTION)
//                .net(Net(bindIp, port, Network.localhostIsIPv6()))
//                .db(dbName)
//                .collection(collection)
//                .upsert(true)
//                .dropCollection(true)
//                .jsonArray(true)
//                .importFile(jsonFile)
//                .build()
//
//        val mongoImportExecutable = MongoImportStarter.getDefaultInstance().prepare(mongoImportConfig)
//        return mongoImportExecutable.start()
//    }
}