package com.mutableconst.intermediary.db

import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.UUID
import java.util.logging.Level
import java.util.logging.Logger

data class RegisterDto(val uuid: UUID,
                       val name: String,
                       val currentIp: String)

private object Sql {
    val getRegisteredByUuid = "select " +
            "name, currentIp " +
            "from register " +
            "where uuid = ?"

    val registerWithIp = "insert or replace " +
            "into register (uuid, name, currentIp) " +
            "values (?, ?, ?)"

    object Columns {
        val name = "name"
        val currentIp = "currentIp"
        val uuid = "uuid"
    }
}

object Register {
    val log = Logger.getLogger(Register.javaClass.name)

    fun register(uuid: UUID, appName: String, currentIp: String): Boolean {
        var connection: Connection? = null
        var registerStatement: Statement? = null

        try {
            connection = DbManager.getConnection()
            registerStatement = connection.prepareStatement(Sql.registerWithIp)
            registerStatement.setString(1, uuid.toString());
            registerStatement.setString(2, appName);
            registerStatement.setString(3, currentIp);

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

    fun getRegistered(uuid: UUID): RegisterDto? {
        var connection: Connection? = null
        var query: Statement? = null
        var dto: RegisterDto? = null

        try {
            connection = DbManager.getConnection()
            query = connection.prepareStatement(Sql.getRegisteredByUuid)
            query.setString(1, uuid.toString())

            val resultSet = query.executeQuery()

            if (resultSet.next()) {
                val name = resultSet.getString(Sql.Columns.name)
                val currentIp = resultSet.getString(Sql.Columns.currentIp)
                dto = RegisterDto(uuid, name, currentIp)
            }
            resultSet.close()
            connection.close()
        } catch (e: SQLException) {
            log.log(Level.SEVERE, e.toString(), e)
        } finally {
            if (query != null) {
                query.close()
            }
            if (connection != null) {
                connection.close()
            }
        }
        return dto
    }
}
