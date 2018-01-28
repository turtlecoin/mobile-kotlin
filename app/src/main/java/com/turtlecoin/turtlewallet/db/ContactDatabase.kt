package com.turtlecoin.turtlewallet.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = true)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context, ContactDatabase::class.java,
                        "contact-book").build()


        fun addContact(alias: String, address: String) = async(CommonPool) {
            INSTANCE?.contactDao()?.insert(Contact(0, alias, address))
        }

        fun deleteContact(id: Long) = async(CommonPool) {
            INSTANCE?.contactDao()?.delete(id)
        }

        fun editContact(id: Long, alias: String, address: String) = async(CommonPool) {
            INSTANCE?.contactDao()?.edit(id, alias, address)
        }

        suspend fun getContacts(): Deferred<List<Contact>> = async(CommonPool) {
            INSTANCE?.contactDao()?.getContacts() as List<Contact>
        }

        fun observeContacts(): LiveData<List<Contact>> = INSTANCE?.contactDao()?.observeContacts()!!
    }
}