package com.turtlecoin.turtlewallet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_send_coin.*
import com.google.zxing.integration.android.IntentIntegrator
import com.turtlecoin.turtlewallet.db.Contact
import com.turtlecoin.turtlewallet.db.DB
import com.turtlecoin.turtlewallet.util.AddressHelper

class SendCoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_coin)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val db = DB()
        val contacts:List<Contact> = db.getContacts()
        val formattedContacts = ArrayList<String>()
        for (c in contacts) {
            formattedContacts.add(AddressHelper().truncate(c.address) + " : " + c.alias)
        }

        val adapter = ArrayAdapter(this, R.layout.dropdown, formattedContacts)
        address_edit.threshold = 0
        address_edit.setAdapter(adapter)
        address_edit.setOnItemClickListener {_, view, pos, _ ->
            val tv = view as TextView
            val txt = tv.text
            address_edit.setText(contacts[pos].address)
        }
        address_edit.setOnClickListener { address_edit.showDropDown() }
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
