package com.turtlecoin.turtlewallet

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        showFragment(TotalBalanceFragment())
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_frame_layout)

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (fragment !is TotalBalanceFragment) {
            //if the current fragment is not the top fragment, go to the top fragment
            showFragment(TotalBalanceFragment(), true)
            (nav_view.menu.getItem(0).setChecked(true))
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.dashboard -> {
                showFragment(TotalBalanceFragment())
            }
            R.id.network_info -> {
                showFragment(NetworkInfoFragment())
            }
            R.id.about -> {
                showFragment(AboutFragment())
            }
            R.id.log_out -> {
                finish()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showFragment(fragment: Fragment, animate: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction()
        if (animate) {
            ft.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
        }
        ft.replace(R.id.main_frame_layout, fragment)
        ft.commit()
    }

    fun walletOnClick(view: View) {
        val intent = Intent(this, WalletActivity::class.java);
        startActivity(intent)
    }
}
