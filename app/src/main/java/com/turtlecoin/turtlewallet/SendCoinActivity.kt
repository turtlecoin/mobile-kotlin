package com.turtlecoin.turtlewallet

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.turtlecoin.turtlewallet.db.ContactDatabase
import com.turtlecoin.turtlewallet.util.AddressHelper
import kotlinx.android.synthetic.main.activity_send_coin.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

class SendCoinActivity : AppCompatActivity() {
    private var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_coin)
        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        job = getContacts()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
    }

    private fun getContacts() = async(UI) {
        try {
            val contacts = ContactDatabase.getContacts().await()
            val formattedContacts = contacts.map{ "${AddressHelper().truncate(it.address)} : ${it.alias}" }
            val adapter = ArrayAdapter(this@SendCoinActivity, R.layout.dropdown, formattedContacts)
            address_edit.threshold = 0
            address_edit.setAdapter(adapter)
            address_edit.setOnItemClickListener {_, view, pos, _ ->
                val tv = view as TextView
                val txt = tv.text
                address_edit.setText(contacts[pos].address)
            }
            address_edit.setOnClickListener { address_edit.showDropDown() }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        finally {
            //Turn off busy indicator.
        }
    }


    // Open QR Intent
    fun readQRCodeOnClick(view: View) {
        val integrator = IntentIntegrator(this@SendCoinActivity)
        integrator.setBeepEnabled(false)
        integrator.initiateScan()
    }

    // Deal with QR Result
    public override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (intent != null) { // Cancelled scanning
            val scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent)
            if (scanResult != null) {
                val contents = intent.getStringExtra("SCAN_RESULT")
                if (AddressHelper().validate(contents)) {
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
}
