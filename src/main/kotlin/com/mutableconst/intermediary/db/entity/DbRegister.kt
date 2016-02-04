package com.mutableconst.intermediary.db.entity

import com.mutableconst.intermediary.db.DbManager
import com.mutableconst.intermediary.dto.RegisterDto
import com.mutableconst.intermediary.etc.use
import org.slf4j.LoggerFactory
import java.sql.SQLException
import java.util.UUID

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
    val log = LoggerFactory.getLogger(DbRegister.javaClass)

    fun register(clientId: UUID, appName: String, currentIp: String): Boolean {
        try {
            DbManager.getConnection().use {
                it.prepareStatement(RegisterSql.registerWithIp).use {
                    it.setString(1, clientId.toString());
                    it.setString(2, appName);
                    it.setString(3, currentIp);

                    it.execute()
                    return true
                }
            }
        } catch (e: SQLException) {
            log.error(e.toString(), e)
        }
        return false
    }

    fun getRegistered(clientId: UUID): RegisterDto? {
        try {
            DbManager.getConnection().use {
                it.prepareStatement(RegisterSql.getRegisteredByUuid).use {
                    it.setString(1, clientId.toString())

                    val resultSet = it.executeQuery()

                    if (resultSet.next()) {
                        val name = resultSet.getString(RegisterSql.Columns.name)
                        val currentIp = resultSet.getString(RegisterSql.Columns.currentIp)
                        return RegisterDto(clientId, name, currentIp)
                    }
                }
            }
        } catch (e: SQLException) {
            log.error(e.toString(), e)
        }
        return null
    }
}
