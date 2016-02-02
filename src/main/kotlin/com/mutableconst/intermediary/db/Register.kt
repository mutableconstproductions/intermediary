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

    object Columns {
        val name = "name"
        val currentIp = "currentIp"
    }
}

object Register {
    val log = Logger.getLogger(Register.javaClass.name)

    fun getRegistered(uuid: String): RegisterDto? {
        var connection: Connection? = null
        var query: Statement? = null
        var dto: RegisterDto? = null

        try {
            connection = DbManager.getConnection()
            query = connection.prepareStatement(Sql.getRegisteredByUuid)
            query.setString(1, uuid)

            val resultSet = query.executeQuery()

            if (resultSet.next()) {
                val name = resultSet.getString(Sql.Columns.name)
                val currentIp = resultSet.getString(Sql.Columns.currentIp)
                val id = UUID.fromString(uuid)
                dto = RegisterDto(id, name, currentIp)
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
