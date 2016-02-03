package com.mutableconst.intermediary.db.entity

import com.mutableconst.intermediary.db.DbManager
import com.mutableconst.intermediary.dto.Contact
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.logging.Level
import java.util.logging.Logger


private object ContactSql {
    val upsert = "insert or replace into contact " +
            "(clientId, firstName, lastName, name, mobile, email) " +
            "values (?, ?, ?, ?, ?, ?)"

    object Columns {
    }
}

object DbContact {
    val log = Logger.getLogger(DbContact.javaClass.name)

    fun save(contact: Contact): Boolean {
        var connection: Connection? = null
        var registerStatement: Statement? = null

        try {
            connection = DbManager.getConnection()
            registerStatement = connection.prepareStatement(ContactSql.upsert)
            registerStatement.setString(1, contact.clientId.toString());
            registerStatement.setString(2, contact.firstName);
            registerStatement.setString(3, contact.lastName);
            val name = getName(contact)
            registerStatement.setString(4, name);
            registerStatement.setString(5, contact.mobile);
            registerStatement.setString(6, contact.email);

            registerStatement.execute()
            return true
        } catch (e: SQLException) {
            log.log(Level.SEVERE, e.toString(), e)
        } finally {
            if (registerStatement != null) {
                registerStatement.close()
            }
            if (connection != null) {
                connection.close()
            }
        }
        return false
    }

    private fun getName(contact: Contact): String {
        var name = contact.name ?: ""
        if (name.isEmpty()) {
            name = (contact.firstName ?: "") + " " + (contact.lastName ?: "")
        }
        return name
    }
}