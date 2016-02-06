package com.mutableconst.intermediary.serv.controller

import com.mutableconst.intermediary.db.entity.DbContact
import com.mutableconst.intermediary.dto.request.ContactList
import com.mutableconst.intermediary.serv.util.RequestUtil
import java.util.UUID
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "GetContacts", value = "/getContacts")
class GetContactController : HttpServlet() {
    override fun doGet(req: HttpServletRequest, resp: HttpServletResponse) {
        val uuid: UUID = UUID.fromString(req.getParameter("uuid"))

        val contacts = DbContact.getContacts(uuid)
        RequestUtil.writeJsonToOutput(resp, ContactList(contacts))
    }
}