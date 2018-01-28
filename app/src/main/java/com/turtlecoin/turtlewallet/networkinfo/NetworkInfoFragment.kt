package com.turtlecoin.turtlewallet.networkinfo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.turtlecoin.turtlewallet.R
import kotlinx.android.synthetic.main.fragment_network_info.*

class NetworkInfoFragment : Fragment() {
    private lateinit var viewModel: NetworkInfoViewModel

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_network_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NetworkInfoViewModel::class.java)
        viewModel.getNetworkInfo().observe(this, Observer { updateUi(it) })
    }

    private fun updateUi(networkInfo: NetworkInfoViewModel.NetworkInfo?){
        if (networkInfo == null){
            Toast.makeText(activity, "Failed to get Network Info, try again", Toast.LENGTH_SHORT).show()
        }else {
            with(networkInfo) {
                hash_rate.text = hashrateString
                difficulty.text = difficultyString
                height.text = heightString
                supply.text = "$supplyString TRTL"
            }
        }
    }

}
