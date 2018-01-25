package com.turtlecoin.turtlewallet

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem


class ViewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_coin)

        // Enable the Up button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_view_contact, menu)
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
                deleteAlert.setMessage("Are you sure you want to delete the contact: " + "Contact #1")

                deleteAlert.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.cancel), {
                    dialogInterface, i ->
                    deleteAlert.cancel()
                })

                deleteAlert.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.delete), {
                    dialogInterface, i ->
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
}