package com.turtlecoin.turtlewallet.db

import android.arch.persistence.room.Room
import android.content.Context

class DB {

    constructor(ct: Context) {
        database = Room.databaseBuilder(ct, ContactDatabase::class.java, "contact-book").allowMainThreadQueries().build()
    }

    constructor() {}

    fun addContact(alias: String, address: String) {
        database?.contactDao()?.insert(Contact(0, alias, address))
    }

    fun deleteContact(id: Long) {
        database?.contactDao()?.delete(id)
    }

    fun editContact(id: Long, alias: String, address: String) {
        database?.contactDao()?.edit(id, alias, address)
    }

    fun getContacts(): List<Contact> {
        return database?.contactDao()?.getContacts() as List<Contact>
    }

    companion object {
        var database: ContactDatabase? = null
    }
}