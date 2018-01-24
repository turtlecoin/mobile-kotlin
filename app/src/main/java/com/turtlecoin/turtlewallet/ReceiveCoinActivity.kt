package com.turtlecoin.turtlewallet

import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_receive_coin.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import android.content.ClipData
import android.content.Context
import android.content.ClipboardManager
import android.widget.Toast


class ReceiveCoinActivity : AppCompatActivity() {

    //Placeholder Address
    val userAddress = "TRTLv1oRF2WBuNUYT9eLb1fhqHFht6nU2fAzuGwoATV23dHMeeBmLbMiatkv3V1iAUVTWduX2HUB8KbWAKqks9bq8xHHyVLf4gr"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_coin)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        qr.setImageBitmap(encodeAsBitmap(userAddress, 400,400))
    }

    // Copy userAddress into Android Clipboard
    fun copyAddress(view: View) {
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
