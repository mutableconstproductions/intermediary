package com.mutableconst.intermediary.qr

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import java.awt.image.BufferedImage
import java.net.Inet4Address
import java.net.NetworkInterface


private fun getLocalhostIp(): Inet4Address? {
    val networkInterfaces = NetworkInterface.getNetworkInterfaces();
    var ret: Inet4Address? = null
    for (network in networkInterfaces) {
        val addresses = network.getInetAddresses()
        for (address in addresses) {
            if (address is Inet4Address && !address.isLoopbackAddress) {
                ret = address
            }
        }
    }
    return ret
}

object Qr {
    val address: Inet4Address? = getLocalhostIp()
    val address_str = "http://" + address?.toString()?.substring(1)

    fun createQrFile(): BufferedImage? {
        val qrGen = QRCodeWriter()
        val bytes = qrGen.encode(address_str, BarcodeFormat.QR_CODE, 256, 256);

        val matrixConfig = MatrixToImageConfig()
        val image = MatrixToImageWriter.toBufferedImage(bytes, matrixConfig)

        return image
    }
}
