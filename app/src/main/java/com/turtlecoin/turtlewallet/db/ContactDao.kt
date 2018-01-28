package com.turtlecoin.turtlewallet.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getContacts(): List<Contact>

    @Query("SELECT * FROM contact")
    fun observeContacts(): LiveData<List<Contact>>

    @Insert
    fun insert(contact: Contact)

    @Query("DELETE FROM contact WHERE cid = :cid")
    fun delete(cid: Long)

    @Query("UPDATE contact SET alias= :alias, address= :address WHERE cid = :cid")
    fun edit(cid: Long, alias: String, address: String)
}