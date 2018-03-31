package com.yunikov.domain.user

import com.yunikov.domain.common.Entity
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*


@Document(collection = User.COLLECTION_NAME)
class User : Entity() {

    @Indexed(unique = true, sparse = true)
    lateinit var email: String
    lateinit var password: String
    var roles: Set<UserRole> = HashSet(0)

    override fun getCollectionName(): String {
        return COLLECTION_NAME
    }

    companion object {
        const val COLLECTION_NAME = "users"
        const val EMAIL = "email"
    }
}