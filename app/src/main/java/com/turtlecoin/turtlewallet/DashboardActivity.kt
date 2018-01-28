package com.turtlecoin.turtlewallet

import android.content.Context
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
import com.turtlecoin.turtlewallet.addressbook.AddressBookFragment
import com.turtlecoin.turtlewallet.networkinfo.NetworkInfoFragment
import com.turtlecoin.turtlewallet.settings.SettingsFragment
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var currentFragment : Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)
           val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)

        if (savedInstanceState != null){
            currentFragment = supportFragmentManager.getFragment(savedInstanceState, FRAGMENT_TAG)
        }else {
            if (intent.getBooleanExtra(SETTING_FRAGMENT, false)) {
                title = getString(R.string.settings)
                showFragment(SettingsFragment())
            } else {
                title = getString(R.string.dashboard)
                showFragment(TotalBalanceFragment())
            }
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_frame_layout)

        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else if (fragment !is TotalBalanceFragment) {
            //if the current fragment is not the top fragment, go to the top fragment
            showFragment(TotalBalanceFragment(), true)
            title = getString(R.string.dashboard)
            (nav_view.menu.getItem(0).setChecked(true))
        } else {
            super.onBackPressed()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        supportFragmentManager.putFragment(outState, FRAGMENT_TAG, currentFragment)
        super.onSaveInstanceState(outState)
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
                title = getString(R.string.dashboard)
                showFragment(TotalBalanceFragment())
            }
            R.id.address_book -> {
                title = getString(R.string.address_book)
                showFragment(AddressBookFragment())
            }
            R.id.network_info -> {
                title = getString(R.string.network_info)
                showFragment(NetworkInfoFragment())
            }
            R.id.drawer_settings -> {
                title = getString(R.string.settings)
                showFragment(SettingsFragment())
            }
            R.id.about -> {
                title = getString(R.string.about)
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
        currentFragment = fragment
    }

    fun walletOnClick(view: View) {
        val intent = Intent(this, WalletActivity::class.java)
        startActivity(intent)
    }

    // From AddressBookFragment
    fun addContactOnClick(view: View) {
        val intent = Intent(this, EditContactActivity::class.java)
        intent.putExtra("flag", false)
        startActivity(intent)

    }

    companion object {
        fun createSettingsIntent(context: Context) : Intent{
            val intent = Intent(context, DashboardActivity::class.java)
            intent.putExtra(SETTING_FRAGMENT, true)
            return intent
        }

        private val SETTING_FRAGMENT = "settings_fragment"
        private val SETTINGS_PROCESSED = "settings_processed"
        private val FRAGMENT_TAG = "current_fragment"
    }
}
