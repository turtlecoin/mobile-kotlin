package com.turtlecoin.turtlewallet.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LastBlockHeader {

    @JsonProperty("id")
    public int id;

    @JsonProperty("jsonrpc")
    public String jsonrpc;

    @JsonProperty("result")
    public Result result;

    public class Result {

        @JsonProperty("block_header")
        public BlockHeader block_header;

        @JsonProperty("status")
        public String status;
        public class BlockHeader {

            @JsonProperty("depth")
            public double depth;

            @JsonProperty("difficulty")
            public long difficulty;

            @JsonProperty("hash")
            public String hash;

            @JsonProperty("height")
            public long height;

            @JsonProperty("major_version")
            public int major_version;

            @JsonProperty("minor_version")
            public int minor_version;

            @JsonProperty("nonce")
            public double nonce;

            @JsonProperty("orphan_status")
            public boolean orphan_status;

            @JsonProperty("prev_hash")
            public String prev_hash;

            @JsonProperty("reward")
            public double reward;

            @JsonProperty("timestamp")
            public double timestamp;
        }
    }




}


