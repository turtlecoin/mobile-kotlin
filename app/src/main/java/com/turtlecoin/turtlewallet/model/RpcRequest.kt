package com.turtlecoin.turtlewallet.model


data class LastBlockHeaderRequest(
            val jsonRpc: String = "2.0",
            val method: String = "getlastblockheader",
            val params: Map<String, String> = emptyMap(),
            val requestId: Int)

data class GetHeightRequest(
        val jsonRpc: String = "2.0",
        val method: String = "getblockcount",
        val params: Map<String, String> = emptyMap(),
        val requestId: Int)

data class GetSupplyRequest(
        val jsonRpc: String = "2.0",
        val method: String = "f_block_json",
        val params: Map<String, String> = emptyMap(),
        val requestId: Int)
