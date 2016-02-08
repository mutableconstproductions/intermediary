package com.mutableconst.intermediary.dto.request

import java.util.UUID


data class Message(val clientId: UUID,
                   val mobile: String,
                   val text: String,
                   val dateSent: Long? = null)