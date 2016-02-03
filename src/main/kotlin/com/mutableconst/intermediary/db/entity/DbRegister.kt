package com.mutableconst.intermediary.db.entity

import com.mutableconst.intermediary.db.DbManager
import com.mutableconst.intermediary.dto.RegisterDto
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.util.UUID
import java.util.logging.Level
import java.util.logging.Logger

private object RegisterSql {
    val getRegisteredByUuid = "select " +
            "name, currentIp " +
            "from register " +
            "where clientId = ?"

    val registerWithIp = "insert or replace " +
            "into register (clientId, name, currentIp) " +
            "values (?, ?, ?)"

    object Columns {
        val name = "name"
        val currentIp = "currentIp"
        val clientId = "clientId"
    }
}

object DbRegister {
    val log = Logger.getLogger(DbRegister.javaClass.name)

    fun register(clientId: UUID, appName: String, currentIp: String): Boolean {
        var connection: Connection? = null
        var registerStatement: Statement? = null

        try {
            connection = DbManager.getConnection()
            registerStatement = connection.prepareStatement(RegisterSql.registerWithIp)
            registerStatement.setString(1, clientId.toString());
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

    fun getRegistered(clientId: UUID): RegisterDto? {
        var connection: Connection? = null
        var query: Statement? = null
        var dto: RegisterDto? = null

        try {
            connection = DbManager.getConnection()
            query = connection.prepareStatement(RegisterSql.getRegisteredByUuid)
            query.setString(1, clientId.toString())

            val resultSet = query.executeQuery()

            if (resultSet.next()) {
                val name = resultSet.getString(RegisterSql.Columns.name)
                val currentIp = resultSet.getString(RegisterSql.Columns.currentIp)
                dto = RegisterDto(clientId, name, currentIp)
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
