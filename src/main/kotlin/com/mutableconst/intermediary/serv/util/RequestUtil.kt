package com.mutableconst.intermediary.serv.util

import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


object RequestUtil {
    fun writeJsonToOutput(res: ServletResponse, any: Any?) {
        res.contentType = "application/json";
        val jsonP = JsonUtil.toJson(any)
        res.writer.print(jsonP)
    }

    fun readJson(req: HttpServletRequest): String {
        val json = StringBuilder()
        val reader = req.reader
        var line = reader.readLine()
        while (line != null) {
            json.append(line)
            line = reader.readLine()
        }
        return json.toString()
    }
}