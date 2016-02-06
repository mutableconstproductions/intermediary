package com.mutableconst.intermediary.dto.request

import java.util.UUID


data class Message(val clientFrom: UUID,
                   val mobile: String,
                   val text: String)