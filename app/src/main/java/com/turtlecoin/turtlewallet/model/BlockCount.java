package com.turtlecoin.turtlewallet.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockCount {

    @JsonProperty("id")
    public int id;

    @JsonProperty("jsonrpc")
    public String jsonrpc;

    @JsonProperty("result")
    public ResultBlockCount result;

    public class ResultBlockCount {

        @JsonProperty("count")
        public long count;

        @JsonProperty("status")
        public String status;
    }
}
