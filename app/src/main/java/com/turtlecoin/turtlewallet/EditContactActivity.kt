package com.turtlecoin.turtlewallet

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.turtlecoin.turtlewallet.util.AddressHelper
import kotlinx.android.synthetic.main.activity_edit_contact.*
import android.app.Activity
import com.turtlecoin.turtlewallet.db.ContactDatabase
import com.turtlecoin.turtlewallet.model.ContactItem
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext


// It can be creating a new contact or editing an existing contact.
class EditContactActivity : AppCompatActivity() {

    var update: Boolean = false
    var contact: ContactItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contact)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        update = intent.extras.getBoolean("flag")

        if(update) {
            contact = intent.extras.getSerializable("contact")!! as ContactItem

            name_edit.setText(contact!!.name)
            address_edit.setText(contact!!.address)
            done_button.setText(R.string.update)
            title = getString(R.string.edit_contact)
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

    fun addOnClick(view: View) {
        val edited_name = name_edit.text.toString()
        val edited_address = address_edit.text.toString()

        if(name_edit.text.isNotEmpty()) {
            if (AddressHelper().validate(edited_address)) {
                launch(UI) {
                    if (update) {
                        ContactDatabase.editContact(contact!!.id, edited_name, edited_address).await()
                    } else {
                        ContactDatabase.addContact(edited_name, edited_address).await()
                    }

                    val returnIntent = Intent()
                    returnIntent.putExtra("edited_name", edited_name)
                    returnIntent.putExtra("edited_address", edited_address)
                    setResult(Activity.RESULT_OK, returnIntent)
                    withContext(UI) {
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, R.string.edit_contact_wrong_address, Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, R.string.edit_contact_no_alias, Toast.LENGTH_LONG).show()
        }
    }
}
