package com.yunikov.api.errors

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

class ErrorResponse @JsonCreator constructor(@param:JsonProperty("code") val code: Int,
                                             @param:JsonProperty("message") val message: String,
                                             @JsonIgnore val httpStatus: HttpStatus)