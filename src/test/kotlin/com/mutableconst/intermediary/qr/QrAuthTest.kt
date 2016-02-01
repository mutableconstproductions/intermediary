package com.mutableconst.intermediary.qr

import org.junit.Assert
import org.junit.Test


class QrAuthTest() {
    @Test
    fun testCreateQrFile_notNull() {
        val image = QrUtil.createQrFile()
        Assert.assertNotNull(image)
    }
}
