package com.turtlecoin.turtlewallet

import android.content.Intent
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.turtlecoin.turtlewallet.util.AddressValidator
import kotlinx.android.synthetic.main.activity_new_contact.*
import kotlinx.android.synthetic.main.fragment_address_book.*

class NewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    // TODO: refactor readQRCodeOnClick and onActivityResult.
    // They are copied from SendCoinActivity. They should share the same code
    // Open QR Intent
    fun readQRCodeOnClick(view: View) {
        val integrator = IntentIntegrator(this)
        integrator.setBeepEnabled(false)
        integrator.initiateScan()
    }

    // Deal with QR Result
    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (intent != null) { // Cancelled scanning
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
            if (scanResult != null) {
                val contents = intent.getStringExtra("SCAN_RESULT")
                if (AddressValidator(contents)) {
                    address_edit.setText(contents)
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

    fun addOnClick(view: View) {
        // TODO: store data locally

        finish()
    }
}
