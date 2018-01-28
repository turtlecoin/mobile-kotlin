package com.turtlecoin.turtlewallet

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.turtlecoin.turtlewallet.model.GetHeightRequest
import com.turtlecoin.turtlewallet.model.GetSupplyRequest
import com.turtlecoin.turtlewallet.model.LastBlockHeaderRequest
import com.turtlecoin.turtlewallet.service.ApiSingleton
import com.turtlecoin.turtlewallet.util.CurrentLocale
import com.turtlecoin.turtlewallet.util.HashFormatter
import kotlinx.android.synthetic.main.fragment_network_info.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import java.text.NumberFormat

class NetworkInfoFragment : Fragment() {

    private lateinit var runningJob: Job

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_network_info, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runningJob = getNetworkInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        runningJob.cancel()
    }

    private fun getNetworkInfo(): Job {
        // Using CommonPool so the lazy instantiation of ApiSingleton and the hashrate math takes places off the main thread
        return launch(CommonPool) {
            try {
                val turtleApi = ApiSingleton.turtleApi
                val lastBlockHeader = turtleApi.getLastBlockHeader(LastBlockHeaderRequest(requestId = ApiSingleton.getRequestId())).await()
                val difficultyVal = lastBlockHeader.result.block_header.difficulty

                val hashrateDouble = difficultyVal.div(30.toDouble())
                val hashrate = HashFormatter(getContext(), hashrateDouble) + "/s"

                val numberFormat = NumberFormat.getNumberInstance(CurrentLocale(getContext()))
                val stringDifficuly = numberFormat.format(difficultyVal)

                val heightVal = turtleApi.getHeight(GetHeightRequest(requestId = ApiSingleton.getRequestId())).await()
                val heightString = numberFormat.format(heightVal.result.count)
                val supplyVal = turtleApi.getSupply(GetSupplyRequest(requestId = ApiSingleton.getRequestId(), params = mapOf("hash" to lastBlockHeader.result.block_header.hash))).await()
                val supplyString = numberFormat.format(supplyVal.result.block.alreadyGeneratedCoins.toDouble().div(100))

                withContext(UI) {
                    hash_rate.text = hashrate
                    difficulty.text = stringDifficuly
                    height.text = heightString
                    supply.text = "$supplyString TRTL"
                }
            } catch (e: Exception){
                Log.e("NetworkInfo", e.message)
            }
        }
    }
}
