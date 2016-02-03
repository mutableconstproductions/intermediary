package com.mutableconst.intermediary.serv.controller

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "AddContact", value = "/addContact")
class AddContactController : HttpServlet() {
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        //val uuid =
    }
}