package com.mutableconst.intermediary.db.entity

import com.mutableconst.intermediary.db.DbManager
import com.mutableconst.intermediary.dto.request.Message
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger


private object MessageSql {
    val insert = "insert into message " +
            "(toClientId, fromClientId, contactId, message) " +
            "values (?, ?, ?, ?)"

    object Columns {
    }
}

object DbMessage {
    val log = Logger.getLogger(DbContact.javaClass.name)

    /**
     * lookup contact via who we're sending to
     * this might change to be id in future
     */
    fun save(message: Message): Boolean {
        val contact = DbContact.getByMobile(message.mobile)
        if (contact == null) {
            log.log(Level.ALL, "Error getting contact for message to " + message.mobile)
            return false
        }

        var connection: Connection? = null
        var saveStatement: Statement? = null
        try {
            connection = DbManager.getConnection()
            saveStatement = connection.prepareStatement(MessageSql.insert)
            saveStatement.setString(1, message.clientTo.toString())
            saveStatement.setString(2, message.clientFrom.toString())
            saveStatement.setInt(3, contact.contactId)
            saveStatement.setString(4, message.text)

            saveStatement.execute()
            return true
        } catch (e: SQLException) {
            log.log(Level.SEVERE, e.toString(), e)
        } finally {
            if (saveStatement != null) {
                saveStatement.close()
            }
            if (connection != null) {
                connection.close()
            }
        }
        return false
    }
}