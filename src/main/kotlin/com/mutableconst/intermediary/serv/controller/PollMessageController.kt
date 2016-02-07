package com.mutableconst.intermediary.serv.controller

import org.slf4j.LoggerFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "PollMessages", value = "/pollMessages")
class PollMessageController : HttpServlet() {
    val log = LoggerFactory.getLogger(PollMessageController::class.java)
    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {

    }
}