package com.turtlecoin.turtlewallet.util

import android.content.Context
import android.os.Build
import java.util.*

      fun CurrentLocale(context: Context): Locale {
        val config = context.resources.configuration
        var sysLocale: Locale? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            sysLocale = config.locales.get(0)
        } else {
            sysLocale = config.locale
        }
        return sysLocale
    }