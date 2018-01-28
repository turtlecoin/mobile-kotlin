package com.turtlecoin.turtlewallet.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Supply(@JsonProperty("id") val id: Int = 0,
                  @JsonProperty("jsonrpc") val jsonrpc: String,
                  @JsonProperty("result") val result: SupplyResult)

data class SupplyResult(
        val block: Block,
        val status: String
)

data class Block(
        val alreadyGeneratedCoins: String,
        val alreadyGeneratedTransactions: Long,
        val baseReward: Long,
        val blockSize: Long,
        val depth: Long,
        val difficulty: Long,
        val effectiveSizeMedian: Long,
        val hash: String,
        val height: Long,
        val minor_version: Int,
        val major_version: Int,
        val nonce: Long,
        val orphan_status: Boolean,
        val penalty: Double,
        val prev_hash: String,
        val reward: Long,
        val sizeMedian: Long,
        val timestamp: Long,
        val totalFeeAmount: Double,
        val transactions: List<Transaction>,
        val transactionsCumulativeSize: Long
)

data class Transaction(
        val amount_out: Long,
        val fee: Double,
        val hash: String,
        val size: Long
)

