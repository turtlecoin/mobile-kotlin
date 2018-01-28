package com.turtlecoin.turtlewallet.networkinfo

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.turtlecoin.turtlewallet.model.GetHeightRequest
import com.turtlecoin.turtlewallet.model.GetSupplyRequest
import com.turtlecoin.turtlewallet.model.LastBlockHeaderRequest
import com.turtlecoin.turtlewallet.service.ApiSingleton
import com.turtlecoin.turtlewallet.util.CurrentLocale
import com.turtlecoin.turtlewallet.util.HashFormatter
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
import java.text.NumberFormat

class NetworkInfoViewModel(application: Application) : AndroidViewModel(application){
    private var networkLiveData: MutableLiveData<NetworkInfo>? = null
    private var job : Job? = null
    fun getNetworkInfo(): MutableLiveData<NetworkInfo> {
        if (networkLiveData == null) {
            networkLiveData = MutableLiveData()
            loadNetworkInfo()
        }
        return networkLiveData as MutableLiveData<NetworkInfo>
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun loadNetworkInfo(){
       job = async(CommonPool) {
           try {
               val turtleApi = ApiSingleton.turtleApi
               val lastBlockHeader = turtleApi.getLastBlockHeader(LastBlockHeaderRequest(requestId = ApiSingleton.getRequestId())).await()
               val difficultyVal = lastBlockHeader.result.block_header.difficulty

               val hashrateDouble = difficultyVal.div(30.toDouble())
               val hashrate = HashFormatter(getApplication(), hashrateDouble) + "/s"

               val numberFormat = NumberFormat.getNumberInstance(CurrentLocale(getApplication()))
               val stringDifficuly = numberFormat.format(difficultyVal)

               val heightVal = turtleApi.getHeight(GetHeightRequest(requestId = ApiSingleton.getRequestId())).await()
               val heightString = numberFormat.format(heightVal.result.count)
               val supplyVal = turtleApi.getSupply(GetSupplyRequest(requestId = ApiSingleton.getRequestId(), params = mapOf("hash" to lastBlockHeader.result.block_header.hash))).await()
               val supplyString = numberFormat.format(supplyVal.result.block.alreadyGeneratedCoins.toDouble().div(100))
               val result = NetworkInfo(hashrate, stringDifficuly, heightString, supplyString)
               networkLiveData?.postValue(result)
           } catch (e: Exception){
               networkLiveData?.postValue(null)
           }
        }
    }


    data class NetworkInfo(val hashrateString: String, val difficultyString: String, val heightString : String, val supplyString: String)
}

