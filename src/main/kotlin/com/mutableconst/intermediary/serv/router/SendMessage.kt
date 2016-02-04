package com.mutableconst.intermediary.serv.router

import com.github.kittinunf.fuel.httpPost
import com.mutableconst.intermediary.db.entity.DbMessage
import com.mutableconst.intermediary.dto.RegisterDto
import com.mutableconst.intermediary.dto.request.Message
import com.mutableconst.intermediary.serv.util.JsonUtil
import org.slf4j.LoggerFactory


private data class Body(val message: String)

object SendMessage {
    private val log = LoggerFactory.getLogger(SendMessage.javaClass)

    fun send(client: RegisterDto, message: Message): Boolean {
        if (!DbMessage.save(message)) {
            log.error("Message sent to invalid contact")
            return false
        }
        val text = JsonUtil.toJson(Body(message.text))

        try {
            val url = "http://" + client.currentIp
            url.httpPost().body(text).response()
        } catch (e: Exception) {
            log.error("Error sending message. ", e)
            return false
        }
        return true
    }
}