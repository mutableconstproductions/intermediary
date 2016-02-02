package com.mutableconst.intermediary.serv

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mutableconst.intermediary.db.Register
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Register", value = "/register")
class RegisterController : HttpServlet() {
    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        val uuid = req.getParameter("uuid");
        val registered = Register.getRegistered(uuid);

        res.contentType = "application/json";

        val writeMapper: ObjectMapper = jacksonObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, false)
        val jsonP = writeMapper.writeValueAsString(registered)
        res.writer.print(jsonP)
    }

    override fun doPost(req: HttpServletRequest, res: HttpServletResponse) {
    }
}
