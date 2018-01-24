package com.turtlecoin.turtlewallet

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class TotalBalanceFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_total_balance, container, false)
    }

    fun walletOnClick(view: View) {
        val intent = Intent(activity, WalletActivity::class.java);
        startActivity(intent)
    }
}
