package com.turtlecoin.turtlewallet

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import android.support.v7.preference.PreferenceManager
import com.turtlecoin.turtlewallet.db.ContactDatabase
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch

class TurtleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val darkModeEnabled = sharedPreferences.getBoolean(getString(R.string.darkmode_setting), false)
        val nightMode = if (darkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        AppCompatDelegate.setDefaultNightMode(nightMode)

        launch(CommonPool) { ContactDatabase.getInstance(applicationContext)  }
    }
}