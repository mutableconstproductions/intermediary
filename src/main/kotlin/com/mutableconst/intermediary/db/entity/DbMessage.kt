package com.mutableconst.intermediary.db.entity

import com.mutableconst.intermediary.db.DbManager
import com.mutableconst.intermediary.dto.request.Message
import com.mutableconst.intermediary.etc.use
import org.slf4j.LoggerFactory
import java.sql.ResultSet
import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.UUID


private object MessageSql {
    val insert = """
            insert into message
            (fromClientId, contactId, message)
            values (?, ?, ?)
            """

    val allMessagesSince = """
            select msg.fromClientId, msg.message, ct.mobile, msg.dateAdded
            from message msg
            join contact ct
            on msg.contactId = ct.contactId
            where msg.dateAdded > ?
            and fromClientId <> ?
            order by msg.messageId asc
            """

    object Columns {
        val message = "message"
        val fromClientId = "fromClientId"
        val mobile = "mobile"
        val dateSent = "dateAdded"
    }
}

object DbMessage {
    val log = LoggerFactory.getLogger(DbMessage.javaClass)

    val dateformat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    private fun extractMessage(resultSet: ResultSet): Message {
        val message = resultSet.getString(MessageSql.Columns.message)
        val fromClientId = UUID.fromString(resultSet.getString(MessageSql.Columns.fromClientId))
        val mobile = resultSet.getString(MessageSql.Columns.mobile)
        val dateSent: Long = resultSet.getLong(MessageSql.Columns.dateSent)
        return Message(fromClientId, mobile, message, dateSent)
    }

    fun getAllMessagesSince(uuid: UUID, timestamp: Long): Collection<Message> {
        val messages: MutableCollection<Message> = arrayListOf()
        try {
            DbManager.getConnection().use {
                it.prepareStatement(MessageSql.allMessagesSince).use {
                    it.setLong(1, timestamp)
                    it.setString(2, uuid.toString())
                    it.executeQuery().use {
                        while (it.next()) {
                            messages.add(extractMessage(it))
                        }
                    }
                }
            }
        } catch (e: SQLException) {
            log.error("""Error polling messages at datetime: $timestamp, ${MessageSql.allMessagesSince}""", e);
        }
        return messages
    }

    /**
     * lookup contact via who we're sending to
     * this might change to be id in future
     */
    fun save(message: Message): Boolean {
        val contact = DbContact.getByMobile(message.mobile)
        if (contact == null) {
            log.error("Error getting contact for message to ${message.mobile}")
            return false
        }

        try {
            DbManager.getConnection().use {
                it.prepareStatement(MessageSql.insert).use {
                    it.setString(1, message.clientId.toString())
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