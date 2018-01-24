package com.turtlecoin.turtlewallet

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_send_coin.*
import com.google.zxing.integration.android.IntentIntegrator
import com.turtlecoin.turtlewallet.util.AddressValidator

class SendCoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_coin)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    // Open QR Intent
    fun readQRCodeOnClick(view: View) {
        val integrator = IntentIntegrator(this@SendCoinActivity)
        integrator.initiateScan()
    }

    // Deal with QR Result
    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (intent != null) { // Cancelled scanning
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
            if (scanResult != null) {
                val contents = intent.getStringExtra("SCAN_RESULT")
                if (AddressValidator(contents)) {
                    address.setText(contents)
                } else {
                    Toast.makeText(this, R.string.qr_scan_wrong, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, R.string.qr_scan_failed, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, R.string.qr_scan_failed, Toast.LENGTH_LONG).show()
        }
    }
}
