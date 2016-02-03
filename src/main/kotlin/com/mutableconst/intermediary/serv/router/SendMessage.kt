package com.mutableconst.intermediary.serv.router

import com.github.kittinunf.fuel.httpPost
import com.mutableconst.intermediary.db.entity.DbMessage
import com.mutableconst.intermediary.dto.RegisterDto
import com.mutableconst.intermediary.dto.request.Message
import com.mutableconst.intermediary.serv.util.JsonUtil


private data class Body(val message: String)

object SendMessage {
    fun send(client: RegisterDto, message: Message): Boolean {
        if (!DbMessage.save(message)) {
            return false
        }

        val text = JsonUtil.toJson(Body(message.text))
        client.currentIp.httpPost().body(text).response()

        return true
    }
}