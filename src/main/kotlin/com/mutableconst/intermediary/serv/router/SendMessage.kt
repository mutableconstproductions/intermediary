package com.mutableconst.intermediary.serv.router

import com.github.kittinunf.fuel.httpPost
import com.mutableconst.intermediary.db.entity.DbMessage
import com.mutableconst.intermediary.dto.RegisterDto
import com.mutableconst.intermediary.dto.request.Message
import com.mutableconst.intermediary.serv.util.JsonUtil
import java.util.logging.Level
import java.util.logging.Logger


private data class Body(val message: String)

object SendMessage {
    private val log = Logger.getLogger(SendMessage.javaClass.name)

    fun send(client: RegisterDto, message: Message): Boolean {
        if (!DbMessage.save(message)) {
            log.log(Level.ALL, "Message sent to invalid contact")
            return false
        }
        val text = JsonUtil.toJson(Body(message.text))

        try {
            client.currentIp.httpPost().body(text).response()
        } catch (e: Exception) {
            log.log(Level.ALL, "Error sending message", e)
            return false
        }
        return true
    }
}