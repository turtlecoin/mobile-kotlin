package com.turtlecoin.turtlewallet.service

import com.turtlecoin.turtlewallet.model.*
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface TurtleApi {
    @POST("json_rpc")
    fun getLastBlockHeader(@Body rpcRequest: LastBlockHeaderRequest) : Deferred<LastBlockHeader>

    @POST("json_rpc")
    fun getHeight(@Body rpcRequest: GetHeightRequest) : Deferred<BlockCount>

    @POST("json_rpc")
    fun getSupply(@Body rpcRequest: GetSupplyRequest) : Deferred<Supply>
}