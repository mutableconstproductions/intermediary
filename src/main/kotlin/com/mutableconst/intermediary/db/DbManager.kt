package com.mutableconst.intermediary.db

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager

// TODO Windows
private object DbConsts {
    val driverName = "org.sqlite.JDBC"

    val fileLocation = System.getProperty("user.home") + "/.local/share/supernova/db/"
    val fileName = "main.db"
    val filePath: Path = Paths.get(fileLocation)
    val jdbcConnectionString = "jdbc:sqlite:" + fileLocation + fileName
}

object DbManager {
    init {
        Class.forName(DbConsts.driverName);
        Files.createDirectories(DbConsts.filePath)
    }

    fun getConnection(): Connection {
        val connection = DriverManager.getConnection(DbConsts.jdbcConnectionString)
        return connection
    }
}