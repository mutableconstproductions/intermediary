package com.mutableconst.intermediary.serv.util

import org.slf4j.LoggerFactory
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Properties
import java.util.UUID


// TODO Windows
object IdentityFile {
    val log = LoggerFactory.getLogger(IdentityFile.javaClass)

    private val fileLocation = System.getProperty("user.home") + "/.local/share/supernova/"
    private val fileName = "identity.properties"
    val file = fileLocation + fileName
    val filePath: Path = Paths.get(fileLocation)
    val identity = File(file)

    fun loadIdentity(): UUID {
        if (!identity.exists()) {
            identity.createNewFile()
        }

        val properties = Properties()
        properties.load(identity.inputStream())
        log.info("Loading identity from: " + identity.absolutePath)
        val uuidProp = properties.getProperty("identity")
        if (uuidProp == null || uuidProp.isEmpty()) {
            val uuid = UUID.randomUUID()
            properties.setProperty("identity", uuid.toString())
            properties.store(identity.outputStream(), null)
            log.info("Created new identity")
            return uuid
        }
        return UUID.fromString(uuidProp)
    }
}

object Identity {
    val uuid: UUID = IdentityFile.loadIdentity()
}