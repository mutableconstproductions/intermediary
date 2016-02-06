package com.mutableconst.intermediary.dto.request

import java.util.UUID


data class Contact(val clientId: UUID,
                   val firstName: String?,
                   val lastName: String?,
                   val name: String?,
                   val mobile: String?,
                   val email: String?,
                   val contactId: Int = 0)


data class ContactList(val data: List<Contact>)