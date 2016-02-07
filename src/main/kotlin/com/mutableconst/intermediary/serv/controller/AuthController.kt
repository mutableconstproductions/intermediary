package com.mutableconst.intermediary.serv.controller

import com.mutableconst.intermediary.qr.QrUtil
import org.slf4j.LoggerFactory
import javax.imageio.ImageIO
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Auth", value = "/auth")
class AuthController : HttpServlet() {
    val log = LoggerFactory.getLogger(AuthController::class.java)
    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        val qrImage = QrUtil.createQrFile()
        log.info("QR Image created")
        val outputStream = res.getOutputStream()
        ImageIO.write(qrImage, "png", outputStream)
        outputStream.close()
    }
}
