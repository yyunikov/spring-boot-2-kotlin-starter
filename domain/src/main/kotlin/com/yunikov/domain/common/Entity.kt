package com.yunikov.domain.common

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version

abstract class Entity : Storable {

    @Id
    private lateinit var id: String

    @CreatedDate
    override var creationTime: Long = 0

    @Version
    override var version: Long = 0

    @LastModifiedDate
    var modificationTime: Long = 0

    // explicitly overriding from Persistable class
    override fun getId(): String? {
        return id
    }

    fun setId(id: String) {
        this.id = id
    }

    companion object {
        const val ID = "_id"
        const val CREATION_TIME = "creationTime"
        const val MODIFICATION_TIME = "modificationTime"
    }
}
