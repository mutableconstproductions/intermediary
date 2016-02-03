package com.mutableconst.intermediary.serv.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mutableconst.intermediary.db.Register
import com.mutableconst.intermediary.serv.util.RequestUtil
import java.util.UUID
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


private object Params {
    val clientId = "clientId"
    val appName = "appName"
}

private data class Response(val status: Boolean)
private data class RegisterRequest(val clientId: UUID, val appName: String)

@WebServlet(name = "Register", value = "/register")
class RegisterController : HttpServlet() {

    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
        val json = RequestUtil.readJson(req)
        val data = jacksonObjectMapper().readValue<RegisterRequest>(json)

        val ip = req.remoteAddr
        val success = Register.register(data.clientId, data.appName, ip)

        RequestUtil.writeJsonToOutput(res, Response(success))
    }
}
