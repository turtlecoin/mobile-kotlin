package com.turtlecoin.turtlewallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ReceiveCoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_coin)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}
