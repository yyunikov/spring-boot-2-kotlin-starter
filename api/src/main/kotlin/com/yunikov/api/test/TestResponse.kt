package com.yunikov.api.test

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class TestResponse @JsonCreator constructor(@param:JsonProperty("message") val test: String)