package com.ezlogin.gateway_data;

import com.ezlogin.log.LogStamp;
import com.google.gson.JsonObject;
import com.sun.istack.internal.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marcf on 05.06.2017.
 */
public class JsonGen {

    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private static String getTime() {
        return "[" + sdf.format(new Date()) + "] ";
    }

    private static String getStamp(@NotNull String log) {
        return JsonGen.getTime() + log + "\n";
    }

    public static String prepClientLogin(String email, String hash, String sToken) {
        JsonObject o = new JsonObject();
        o.addProperty("email", email);
        o.addProperty("hash", hash);
        o.addProperty("token", sToken);
        return o.toString();
    }
}
