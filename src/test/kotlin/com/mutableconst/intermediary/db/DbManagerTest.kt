package com.mutableconst.intermediary.db

import org.junit.Assert
import org.junit.Test


class DbManagerTest {
    @Test
    fun test() {
        val connection = DbManager.getConnection()
        Assert.assertNotNull(connection)
    }
}