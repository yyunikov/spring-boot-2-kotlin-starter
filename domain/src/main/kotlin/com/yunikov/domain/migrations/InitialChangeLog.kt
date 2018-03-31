package com.yunikov.domain.migrations

import com.mongodb.DB
import org.slf4j.LoggerFactory
import java.io.IOException

// @ChangeLog(order = "001")
class InitialChangeLog {

    // @ChangeSet(order = "001", id = "init", author = "yyunikov")
    @Throws(IOException::class)
    fun init(db: DB) {
    }

    companion object {
        private val log = LoggerFactory.getLogger(InitialChangeLog::class.java)
    }
}
