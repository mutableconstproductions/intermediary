package com.mutableconst.intermediary.dto

import java.util.UUID

data class RegisterDto(val uuid: UUID,
                       val name: String,
                       val currentIp: String)