package com.mutableconst.intermediary.serv.controller

import com.mutableconst.intermediary.db.Register
import com.mutableconst.intermediary.serv.JsonUtil
import java.util.UUID
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


private object Params {
    val uuid = "uuid"
    val appName = "appName"
}

private data class Response(val status: Boolean)

@WebServlet(name = "Register", value = "/register")
class RegisterController : HttpServlet() {

    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
        val uuid = UUID.fromString(req.getParameter(Params.uuid))
        val appName = req.getParameter(Params.appName)
        val ip = req.remoteAddr
        val success = Register.register(uuid, appName, ip)

        JsonUtil.writeJsonToOutput(res, Response(success))
    }
}
