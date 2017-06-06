package com.ezlogin.gateway_data;

import com.sun.istack.internal.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marcf on 05.06.2017.
 */
public class JsonGen {

    private static final String JSON_PARSE_ERROR = "json_parse_error";

    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private static String getTime() {
        return "[" + sdf.format(new Date()) + "] ";
    }

    private static String getStamp(@NotNull String log) {
        return JsonGen.getTime() + log + "\n";
    }

    /*JSON Request from Client*/
    public static String prepClientLogin(String action, String email, String hash, String sToken) {
        JSONObject o = new JSONObject();
        o.put("action", action);
        o.put("email", email);
        o.put("hash", hash);
        o.put("token", sToken);
        return o.toJSONString();
    }

    /*User login request gets transformed */
    public static String prepClientLoginGateway(String clientRequest, String action, String masterToken) {
        String email;
        String hash;
        JSONParser p = new JSONParser();
        JSONObject jObject = null;
        try {
            jObject = (JSONObject) p.parse(clientRequest);
        } catch (ParseException e) {
            return JsonGen.JSON_PARSE_ERROR;
        }

        email = (String) jObject.get("email");
        hash = (String) jObject.get("hash");

        JSONObject o = new JSONObject();
        o.put("action", action);
        o.put("email", email);
        o.put("hash", hash);
        o.put("master-token", masterToken);
        return o.toJSONString();
    }
}
