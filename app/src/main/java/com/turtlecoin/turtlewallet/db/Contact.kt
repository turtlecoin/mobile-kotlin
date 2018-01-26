package com.turtlecoin.turtlewallet.db

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Contact(
        @PrimaryKey(autoGenerate = true)
        val cid: Long,
        val alias: String = "",
        val address: String = ""
)