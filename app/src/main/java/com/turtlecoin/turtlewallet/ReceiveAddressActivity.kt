package com.turtlecoin.turtlewallet

import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_receive_address.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import android.content.ClipData
import android.content.Context
import android.content.ClipboardManager
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.turtlecoin.turtlewallet.db.ContactDatabase
import com.turtlecoin.turtlewallet.model.ContactItem
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch


class ReceiveAddressActivity : AppCompatActivity() {

    // TODO get editable flag from intent
    var editable = false

    var contact: ContactItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_address)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        contact = intent.extras.getSerializable("contact") as ContactItem
        editable = intent.extras.getBoolean("editable")

        btn_address.text = contact!!.address

        qr.setImageBitmap(encodeAsBitmap(contact!!.address, 400, 400))

        if (editable) {
            title = contact!!.name
        } else {
            title = contact!!.address
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (!editable) {
            return false
        }
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_edit_contact, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                val intent = Intent(this, EditContactActivity::class.java)
                intent.putExtra("contact", contact)
                intent.putExtra("flag", true)
                startActivityForResult(intent, 1337)
                return true
            }
            R.id.action_delete -> {
                val deleteAlert = AlertDialog.Builder(this).create()
                deleteAlert.setTitle(getString(R.string.delete))

                deleteAlert.setMessage(String.format(getString(R.string.delete_contact_alert_text), contact!!.name))

                deleteAlert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), { dialogInterface, _ ->
                    dialogInterface.cancel()
                })

                deleteAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.delete), { _, _ ->
                    launch(UI) {
                        ContactDatabase.deleteContact(contact!!.id).await()
                        finish()
                    }
                })

                deleteAlert.show()
                return true
            }
            android.R.id.home -> {
                finish()
            }
            else -> return super.onOptionsItemSelected(item)
        }

        return super.onOptionsItemSelected(item)
    }

    // Copy userAddress into Android Clipboard
    fun copyAddressOnClick(view: View) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Address", contact!!.address)
        clipboard.primaryClip = clip
        Toast.makeText(this, R.string.clipboard_copied, Toast.LENGTH_SHORT).show()
    }

    // Generate QR Code using a String (TRTL Address) **/
    private fun encodeAsBitmap(str: String, WIDTH: Int, HEIGHT: Int): Bitmap? {
        val result: BitMatrix
        try {
            result = MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null)
        } catch (thr: Throwable) {
            // Unsupported format
            return null
        }

        val w = result.width
        val h = result.height
        val pixels = IntArray(w * h)
        for (y in 0 until h) {
            val offset = y * w
            for (x in 0 until w) {
                pixels[offset + x] = if (result.get(x, y)) Color.BLACK else Color.WHITE
            }
        }
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h)
        return bitmap
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try {
            val edited_name = data!!.extras.getString("edited_name")
            val edited_address = data!!.extras.getString("edited_address")
            title = edited_name
            if (contact!!.address != edited_address) {
                btn_address.text = edited_address
                qr.setImageBitmap(encodeAsBitmap(edited_address, 400, 400))
            }
        } catch (re: RuntimeException) {
        }
    }
}
