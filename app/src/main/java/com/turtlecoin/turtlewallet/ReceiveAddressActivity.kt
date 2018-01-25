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


class ReceiveAddressActivity : AppCompatActivity() {

    //Placeholder Address
    val userAddress = "TRTLv1oRF2WBuNUYT9eLb1fhqHFht6nU2fAzuGwoATV23dHMeeBmLbMiatkv3V1iAUVTWduX2HUB8KbWAKqks9bq8xHHyVLf4gr"

    // TODO get editable flag from intent
    val editable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_address)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        qr.setImageBitmap(encodeAsBitmap(userAddress, 400,400))

        if (editable) {
            title = "Contact #1" // replace it with the contact's name
        } else {
            title = getString(R.string.receive_address)
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
                // TODO send the current contactItem
                val intent = Intent(this, EditContactActivity::class.java);
                startActivity(intent)
                return true
            }
            R.id.action_delete -> {
                val deleteAlert = AlertDialog.Builder(this).create()
                deleteAlert.setTitle(getString(R.string.delete))

                val contactName = "Contact #1"  // TODO: replace it with actual name
                deleteAlert.setMessage(String.format(getString(R.string.delete_contact_alert_text), contactName))

                deleteAlert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), {
                    dialogInterface, _ ->
                    dialogInterface.cancel()
                })

                deleteAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.delete), {
                    _, _ ->
                    // TODO Delete the item from the storage
                    finish()
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
        val clip = ClipData.newPlainText("Address", userAddress)
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
}
