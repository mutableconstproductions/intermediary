package com.mutableconst.intermediary.serv.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mutableconst.intermediary.db.entity.DbContact
import com.mutableconst.intermediary.dto.Response
import com.mutableconst.intermediary.dto.request.Contact
import com.mutableconst.intermediary.serv.util.RequestUtil
import org.slf4j.LoggerFactory
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "AddContact", value = "/addContact")
class AddContactController : HttpServlet() {
    val log = LoggerFactory.getLogger(AddContactController::class.java)
    override fun doPost(req: HttpServletRequest, resp: HttpServletResponse) {
        val postData = RequestUtil.readJson(req)
        val contact = jacksonObjectMapper().readValue<Contact>(postData)

        log.info(contact.toString())
        val saved = DbContact.save(contact)
        RequestUtil.writeJsonToOutput(resp, Response(saved))
    }
}