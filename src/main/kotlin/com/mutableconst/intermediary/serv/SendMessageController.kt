package com.mutableconst.intermediary.serv

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Send Message", value = "/sendMessage")
class SendMessageController : HttpServlet() {
    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {

    }
}
