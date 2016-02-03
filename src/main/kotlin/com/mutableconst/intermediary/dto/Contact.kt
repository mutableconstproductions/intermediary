package com.mutableconst.intermediary.dto

import java.util.UUID


data class Contact(val clientId: UUID,
                      val firstName: String?,
                      val lastName: String?,
                      val name: String?,
                      val mobile: String?,
                      val email: String?)