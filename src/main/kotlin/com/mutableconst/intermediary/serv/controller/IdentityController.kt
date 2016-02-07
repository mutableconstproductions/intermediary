package com.mutableconst.intermediary.serv.controller

import com.mutableconst.intermediary.serv.util.Identity
import com.mutableconst.intermediary.serv.util.RequestUtil
import org.slf4j.LoggerFactory
import java.util.UUID
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


data class IdentityResponse(val identity: UUID)

@WebServlet(name = "Identity", value = "/identity")
class IdentityController : HttpServlet() {
    val log = LoggerFactory.getLogger(IdentityController::class.java)
    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        RequestUtil.writeJsonToOutput(res, IdentityResponse(Identity.uuid))
    }
}