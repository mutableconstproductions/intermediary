package com.mutableconst.intermediary.db.entity

import com.mutableconst.intermediary.db.DbManager
import com.mutableconst.intermediary.dto.request.Message
import com.mutableconst.intermediary.etc.use
import org.slf4j.LoggerFactory
import java.sql.SQLException


private object MessageSql {
    val insert = "insert into message " +
            "(fromClientId, contactId, message) " +
            "values (?, ?, ?)"

    object Columns {
    }
}

object DbMessage {
    val log = LoggerFactory.getLogger(DbMessage.javaClass)

    /**
     * lookup contact via who we're sending to
     * this might change to be id in future
     */
    fun save(message: Message): Boolean {
        val contact = DbContact.getByMobile(message.mobile)
        if (contact == null) {
            log.error("Error getting contact for message to " + message.mobile)
            return false
        }

        try {
            DbManager.getConnection().use {
                it.prepareStatement(MessageSql.insert).use {
                    it.setString(1, message.clientFrom.toString())
                    it.setInt(2, contact.contactId)
                    it.setString(3, message.text)

                    it.execute()
                    return true
                }
            }
        } catch (e: SQLException) {
            log.error(e.toString(), e)
        }
        return false
    }
}