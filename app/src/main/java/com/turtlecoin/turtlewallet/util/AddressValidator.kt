package com.turtlecoin.turtlewallet.util

fun AddressValidator(address: String): Boolean {
    return (address.length == 99 && address.startsWith("TRTL"))
}