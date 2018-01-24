package com.turtlecoin.turtlewallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SendCoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_coin)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun readQRCodeOnClick(view: View) {
        // TODO: Start intent of QR code reader
    }
}
