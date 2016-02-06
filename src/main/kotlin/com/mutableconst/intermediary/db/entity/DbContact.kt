package com.mutableconst.intermediary.db.entity

import com.mutableconst.intermediary.db.DbManager
import com.mutableconst.intermediary.dto.request.Contact
import com.mutableconst.intermediary.etc.use
import org.slf4j.LoggerFactory
import java.sql.ResultSet
import java.util.UUID


private object ContactSql {
    val upsert = "insert or replace into contact " +
            "(clientId, firstName, lastName, name, mobile, email) " +
            "values (?, ?, ?, ?, ?, ?)"

    val byMobile = "select contactId, clientId, firstName, lastName, name, mobile, email " +
            "from contact " +
            "where mobile = ?"

    val getAllContactsForClient =  "select contactId, clientId, firstName, lastName, name, mobile, email " +
            "from contact " +
            "where clientId = ?"

    object Columns {
        val contactId = "contactId"
        val clientId = "clientId"
        val firstName = "firstName"
        val lastName = "lastName"
        val name = "name"
        val mobile = "mobile"
        val email = "email"
    }
}


object DbContact {
    val log = LoggerFactory.getLogger(DbContact.javaClass)

    fun getByMobile(mobile: String): Contact? {
        try {
            DbManager.getConnection().use {
                it.prepareStatement(ContactSql.byMobile).use {
                    it.setString(1, mobile)
                    val resultSet: ResultSet = it.executeQuery()

                    if (resultSet.next()) {
                        return extractContact(resultSet)
                    }
                }
            }
        } catch(e: Exception) {
            log.error(e.toString(), e)
        }
        return null
    }

    private fun extractContact(resultSet: ResultSet): Contact {
        val clientId = UUID.fromString(resultSet.getString(ContactSql.Columns.clientId))
        val contactId = resultSet.getInt(ContactSql.Columns.contactId)
        val firstName = resultSet.getString(ContactSql.Columns.firstName)
        val lastName = resultSet.getString(ContactSql.Columns.lastName)
        val name = resultSet.getString(ContactSql.Columns.name)
        val email = resultSet.getString(ContactSql.Columns.email)
        val mobile = resultSet.getString(ContactSql.Columns.mobile)

        return Contact(clientId, firstName, lastName, name, mobile, email, contactId)
    }

    fun save(contact: Contact): Boolean {
        try {
            DbManager.getConnection().use {
                it.prepareStatement(ContactSql.upsert).use {
                    it.setString(1, contact.clientId.toString());
                    it.setString(2, contact.firstName);
                    it.setString(3, contact.lastName);
                    val name = getName(contact)
                    it.setString(4, name);
                    it.setString(5, contact.mobile);
                    it.setString(6, contact.email);

                    it.execute()
                    return true
                }
            }
        } catch(e: Exception) {
            log.error(e.toString(), e)
        }
        return false
    }

    fun getContacts(clientId: UUID): List<Contact> {
        try {
            DbManager.getConnection().use {
                it.prepareStatement(ContactSql.getAllContactsForClient).use {
                    it.setString(1, clientId.toString())
                    val resultSet = it.executeQuery()
                    val contacts: MutableList<Contact> = arrayListOf()
                    while (resultSet.next()) {
                        contacts.add(extractContact(resultSet))
                    }
                    return contacts
                }
            }
        } catch(e: Exception) {
            log.error(e.toString(), e)
        }
        return emptyList()
    }

    private fun getName(contact: Contact): String {
        var name = contact.name ?: ""
        if (name.isEmpty()) {
            name = (contact.firstName ?: "") + " " + (contact.lastName ?: "")
        }
        return name
    }
}