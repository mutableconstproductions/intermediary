package com.mutableconst.intermediary.serv.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper


object JsonUtil {
    fun toJson(o: Any?): String {
        if (o == null) {
            return ""
        }

        val writeMapper: ObjectMapper = jacksonObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, false)
        val jsonP = writeMapper.writeValueAsString(o)
        return jsonP
    }
}