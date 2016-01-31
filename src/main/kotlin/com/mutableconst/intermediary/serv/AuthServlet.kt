package com.mutableconst.intermediary.serv

import com.mutableconst.intermediary.qr.Qr
import javax.imageio.ImageIO
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@WebServlet(name = "Auth", value = "/auth")
class AuthController : HttpServlet() {
    override fun doGet(req: HttpServletRequest, res: HttpServletResponse) {
        val qrImage = Qr.createQrFile()
        val outputStream = res.getOutputStream()
        ImageIO.write(qrImage, "png", outputStream)
        outputStream.close()
    }
}
