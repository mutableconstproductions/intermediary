package com.mutableconst.intermediary.db

import com.mutableconst.intermediary.etc.use
import org.slf4j.LoggerFactory
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.sql.Connection
import java.sql.DriverManager

// TODO Windows
private object DbConsts {
    val dbSchema = "src/main/resources/sql/dbInit.sql"

    val driverName = "org.sqlite.JDBC"

    val fileLocation = System.getProperty("user.home") + "/.local/share/supernova/db/"
    val fileName = "main.db"
    val filePath: Path = Paths.get(fileLocation)
    val jdbcConnectionString = "jdbc:sqlite:" + fileLocation + fileName
}

object DbManager {
    val log = LoggerFactory.getLogger(DbManager.javaClass)
    init {
        Class.forName(DbConsts.driverName);
        Files.createDirectories(DbConsts.filePath)

        // exec dbInit.sql
        val sb = StringBuilder()
        log.debug(File(DbConsts.dbSchema).absolutePath.toString())
        BufferedReader(FileReader(File(DbConsts.dbSchema))).use {
            var line = it.readLine()
            while (line != null) {
                sb.append(line).append("\n")
                line = it.readLine()
            }
        }
        log.debug(sb.toString())
        DriverManager.getConnection(DbConsts.jdbcConnectionString).use {
            it.createStatement().use {
                it.executeUpdate(sb.toString())
                log.info("Exec Done")
            }
        }
    }

    fun getConnection(): Connection {
        val connection = DriverManager.getConnection(DbConsts.jdbcConnectionString)
        return connection
    }
}
