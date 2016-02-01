package com.mutableconst.intermediary.qr

import java.net.Inet4Address
import java.net.NetworkInterface


object IpUtil {
    fun getLocalhostIp(): Inet4Address? {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces();
        var ret: Inet4Address? = null
        for (network in networkInterfaces) {
            val addresses = network.inetAddresses
            for (address in addresses) {
                if (address is Inet4Address && !address.isLoopbackAddress) {
                    ret = address
                }
            }
        }
        return ret
    }
}
