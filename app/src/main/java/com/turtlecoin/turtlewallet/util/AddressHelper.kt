package com.turtlecoin.turtlewallet.util

class AddressHelper {
    fun validate(address: String): Boolean {
        return (address.length == 99 && address.startsWith("TRTL"))
    }
    fun truncate(address: String): String {
        return address.substring(0, 10) + "..." + address.substring(address.lastIndex-9, address.lastIndex)
    }
}
