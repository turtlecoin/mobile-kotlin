//----------------------------------------------------------------------------------------
//
// 	Originally from:
// 	https://github.com/CaptainMeatloaf/TurtleBot/blob/master/Utilities/HashFormatter.cs
//
// 	Modified on January 23, 2018
//
//-----------------------------------------------------------------------------------------

package com.turtlecoin.turtlewallet.util

import android.content.Context

fun HashFormatter(context: Context, hashrate: Double): String {
    var hashrate = hashrate
    var index = 0
    val byteUnits = arrayOf(" H", " KH", " MH", " GH", " TH", " PH")
    while (hashrate > 1000) {
        hashrate = hashrate / 1000
        index++
    }
    return String.format(CurrentLocale(context), "%.2f", hashrate) + byteUnits[index]
}