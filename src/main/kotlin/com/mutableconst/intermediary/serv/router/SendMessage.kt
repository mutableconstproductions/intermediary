package com.mutableconst.intermediary.serv.router

import com.github.kittinunf.fuel.httpPost
import com.mutableconst.intermediary.dto.RegisterDto
import com.mutableconst.intermediary.dto.request.Message
import com.mutableconst.intermediary.serv.util.JsonUtil
import org.slf4j.LoggerFactory


private data class Body(val message: String)

object SendMessage {
    private val log = LoggerFactory.getLogger(SendMessage.javaClass)

    fun sendAll(clients: Collection<RegisterDto>, message: Message) {
        val text = JsonUtil.toJson(message)
        for (client in clients) {
            try {
                val url = "http://" + client.currentIp
                url.httpPost().body(text).response()
            } catch (e: Exception) {
                log.error("Error sending message: " + message + " to " + client.uuid, e)
            }
        }
    }
}