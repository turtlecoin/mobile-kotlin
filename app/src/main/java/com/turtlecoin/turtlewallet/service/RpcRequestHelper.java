package com.turtlecoin.turtlewallet.service;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.turtlecoin.turtlewallet.model.BlockCount;
import com.turtlecoin.turtlewallet.model.LastBlockHeader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RpcRequestHelper {
    private static int requestId = 0;

    private static String SendRpcRequest(String method, String params) {
        try {
            URL url = new URL("http://us.turtlepool.space:11899/json_rpc");
            String requstJson  = String.format("{ \"jsonrpc\":\"2.0\", \"method\":\"%s\", \"params\":%s, \"id\":%d }", method, params, requestId++);

            HttpURLConnection connction = (HttpURLConnection) url.openConnection();
            connction.setRequestMethod("POST");
            connction.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            OutputStream os = connction.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(requstJson);
            writer.flush();
            writer.close();
            os.close();

            connction.connect();

            if (connction.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + connction.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connction.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                Log.d("[DEBUG]" , "json output:" + output);
               return output;
            }

            connction.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static LastBlockHeader GetLastBlockHeader() {
        String jsonOutput = SendRpcRequest("getlastblockheader", "{}");

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonOutput, LastBlockHeader.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long GetHeight() {
        String jsonOutput = SendRpcRequest("getblockcount", "{}");
        ObjectMapper mapper = new ObjectMapper();
        try {
            BlockCount blockCount = mapper.readValue(jsonOutput, BlockCount.class);
            return blockCount.result.count;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double GetSupply(String hash) {
        String params = String.format("{ \"hash\":\"%s\"}", hash);
        String jsonOutput = SendRpcRequest("f_block_json", params);
        String supply = extractSupply(jsonOutput);
        return Double.valueOf(supply) / 100;
    }

    // This is hacky but it works!!
    private static String extractSupply(String json) {
        Pattern p = Pattern.compile("alreadyGeneratedCoins...\\p{Digit}+");
        Matcher m = p.matcher(json);

        if (m.find())
        {
            String theGroup = m.group(0);
            String supply = theGroup.substring(24);
            return supply;
        }
        return "";
    }

}
