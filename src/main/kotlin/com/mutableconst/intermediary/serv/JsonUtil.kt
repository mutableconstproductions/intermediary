package com.mutableconst.intermediary.serv

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.servlet.ServletResponse


object JsonUtil {
    private fun toJson(o: Any?): String {
        if (o == null) {
            return ""
        }

        val writeMapper: ObjectMapper = jacksonObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, false)
        val jsonP = writeMapper.writeValueAsString(o)
        return jsonP
    }

    fun writeJsonToOutput(res: ServletResponse, any: Any?) {
        res.contentType = "application/json";
        val jsonP = JsonUtil.toJson(any)
        res.writer.print(jsonP)
    }
}