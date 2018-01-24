package com.turtlecoin.turtlewallet

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.turtlecoin.turtlewallet.service.RpcRequestHelper
import com.turtlecoin.turtlewallet.util.HashFormatter
import java.text.NumberFormat
import com.turtlecoin.turtlewallet.util.CurrentLocale

class NetworkInfoFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater!!.inflate(R.layout.fragment_network_info, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val thread = Thread(Runnable {
            try {
                val lastBlockHeader = RpcRequestHelper.GetLastBlockHeader();
                val difficulty = lastBlockHeader.result.block_header.difficulty
                val hashrateDouble = difficulty.div(30.toDouble())
                val hashrate = HashFormatter(context, hashrateDouble) + "/s"

                val numberFormat  = NumberFormat.getNumberInstance(CurrentLocale(context))
                val stringDifficuly = numberFormat.format(difficulty)

                val height = RpcRequestHelper.GetHeight()
                val supply = RpcRequestHelper.GetSupply(lastBlockHeader.result.block_header.hash)

                activity.runOnUiThread( {
                    activity.findViewById<TextView>(R.id.hash_rate).setText(hashrate)
                    activity.findViewById<TextView>(R.id.difficulty).setText(stringDifficuly)
                    activity.findViewById<TextView>(R.id.height).setText(numberFormat.format(height))
                    activity.findViewById<TextView>(R.id.supply).setText(numberFormat.format(supply) + " TRTL")
                })
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        thread.start();
    }

}
