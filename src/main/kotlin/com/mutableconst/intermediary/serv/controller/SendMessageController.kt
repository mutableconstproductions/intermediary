package com.mutableconst.intermediary.serv.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mutableconst.intermediary.db.entity.DbMessage
import com.mutableconst.intermediary.db.entity.DbRegister
import com.mutableconst.intermediary.dto.RegisterDto
import com.mutableconst.intermediary.dto.Response
import com.mutableconst.intermediary.dto.request.Message
import com.mutableconst.intermediary.serv.util.RequestUtil
import org.slf4j.LoggerFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Send Message", value = "/sendMessage")
class SendMessageController : HttpServlet() {
    val log = LoggerFactory.getLogger(SendMessageController::class.java)

    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
        val postData = RequestUtil.readJson(req)
        val message = jacksonObjectMapper().readValue<Message>(postData)
        log.info(message.toString());

        val allRegistered: List<RegisterDto> = DbRegister.getAllRegisteredFor(message.clientFrom)
        DbMessage.save(message)

        // TODO actually send it
        //RequestUtil.writeJsonToOutput(res, allRegistered)
        RequestUtil.writeJsonToOutput(res, Response(true))
    }
}
