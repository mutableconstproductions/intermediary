package com.mutableconst.intermediary.serv.controller

import com.mutableconst.intermediary.db.entity.DbMessage
import com.mutableconst.intermediary.serv.util.RequestUtil
import org.slf4j.LoggerFactory
import java.util.UUID
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "PollMessages", value = "/pollMessages")
class PollMessageController : HttpServlet() {
    val log = LoggerFactory.getLogger(PollMessageController::class.java)
    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        val uuid: UUID = UUID.fromString(req.getParameter("uuid"))
        val lastChecked = req.getParameter("lastChecked").toLong()

        val messages = DbMessage.getAllMessagesSince(uuid, lastChecked)
        RequestUtil.writeJsonToOutput(res, messages)
    }
}