package com.turtlecoin.turtlewallet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.turtlecoin.turtlewallet.util.AddressValidator
import kotlinx.android.synthetic.main.activity_edit_contact.*

// It can be creating a new contact or editing an existing contact.
class EditContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Todo: get update flag from intent
        val update = false

        if (update) {
            done_button.setText(R.string.update)
        } else {
            done_button.setText(R.string.add)
        }
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
        // TODO: if it's a new contact, store the data locally. else, update the data

        finish()
    }
}
