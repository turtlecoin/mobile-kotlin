package com.turtlecoin.turtlewallet

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_wallet.*
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.view.View
import com.turtlecoin.turtlewallet.model.ContactItem
import java.io.Serializable

class WalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)
        setSupportActionBar(toolbar)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsingToolbarLayout)
        val appBarLayout = findViewById<AppBarLayout>(R.id.appBarLayout)
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var titleIsShowing = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = "12.3MM TRTL"
                    titleIsShowing = true
                } else if (titleIsShowing) {
                    collapsingToolbarLayout.title = " "//careful there should a space between double quote otherwise it wont work
                    titleIsShowing = false
                }
            }
        })
    }

    fun sendOnClick(view: View) {
        startActivity(Intent(this, SendCoinActivity::class.java))
    }

    fun receiveOnClick(view: View) {
        val testAddress = "TRTLabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"
        val intent = Intent(this, ReceiveAddressActivity::class.java)
        intent.putExtra("contact", ContactItem(-1, "", testAddress) as Serializable)
        intent.putExtra("editable", false)
        this.startActivity(intent)
    }
}
