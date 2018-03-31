package com.yunikov.domain.common

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.domain.Persistable

interface Storable : Persistable<String> {

    var creationTime: Long

    var version: Long

    @JsonIgnore
    fun getCollectionName(): String

    @JsonIgnore
    override fun isNew(): Boolean {
        return version == 0L && creationTime == 0L
    }
}
