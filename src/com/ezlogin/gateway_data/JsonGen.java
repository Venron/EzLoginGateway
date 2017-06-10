package com.ezlogin.gateway_data;

import com.sun.istack.internal.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by marcf on 05.06.2017.
 */
public class JsonGen {

    private static final String JSON_PARSE_ERROR = "json_parse_error";
    private static final String RESPONSE_USER_FOUND = "user_found";
    private static final String RESPONSE_USER_NOT_FOUND = "user_not_found";

    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private static String getTime() {
        return "[" + sdf.format(new Date()) + "] ";
    }

    private static String getStamp(@NotNull String log) {
        return JsonGen.getTime() + log + "\n";
    }

    /*
    * JSON Request from Client
    * Client -> GS
    * */
    public static String getJsonClientLogin(String email, String hash, String sToken) {
        String action = "request_login";
        JSONObject o = new JSONObject();
        o.put("action", action);
        o.put("email", email);
        o.put("hash", hash);
        o.put("token", sToken);
        return o.toJSONString();
    }

    /*
    * User login request gets transformed
    * Replace the session token with the master token for the communication between GS and AS
    * GS -> GS
    * */
    private static String getJsonClientLoginGateway(String clientRequest, String masterToken) {
        String action = "request_login";
        String email;
        String hash;
        JSONParser p = new JSONParser();
        JSONObject o = null;
        try {
            o = (JSONObject) p.parse(clientRequest);
        } catch (ParseException e) {
            return JsonGen.JSON_PARSE_ERROR;
        }

        email = (String) o.get("email");
        hash = (String) o.get("hash");

        JSONObject ob = new JSONObject(new HashMap<String, String>());
        ob.put("action", action);
        ob.put("email", email);
        ob.put("hash", hash);
        ob.put("master-token", masterToken);
        return ob.toJSONString();
    }

    /*
    * Request to authentication server to check for the specified user
    * GS -> AS
    * */
    public static String getJsonRequestUserValidation(String clientRequest, String email, String hash, String masterToken) {
        JSONParser p = new JSONParser();
        JSONObject ob = null;
        try {
            ob = (JSONObject) p.parse(JsonGen.getJsonClientLoginGateway(clientRequest, masterToken));
        } catch (ParseException e) {
            return JsonGen.JSON_PARSE_ERROR;
        }
        String action = "request_check_for_user";
        JSONObject o = new JSONObject();
        o.put("action", action);
        o.put("email", email);
        o.put("hash", hash);
        o.put("token", masterToken);
        return o.toJSONString();
    }

    /*
    * Response from the authentication server if the AS has found a corresponding registered user
    * AS -> GS
    * */
    public static String getJsonUserCheckResponse(String email, String answer, String masterToken) {
        String action = "response_check_for_user";
        JSONObject o = new JSONObject();
        o.put("action", action);
        o.put("email", email);
        o.put("answer", answer);
        o.put("token", masterToken);
        return o.toJSONString();
    }

    /*
    * Response from the GS to the Client with either login granted or login denied
    * GS -> Client
    * */
    public static String getJsonUserLoginResponse(String email, boolean found, String sToken) {
        String action = "response_login";
        JSONObject o = new JSONObject();
        o.put("action", action);
        o.put("email", email);
        o.put("token", sToken);
        if(found) {
            o.put("response", JsonGen.RESPONSE_USER_FOUND);
        } else {
            o.put("response", JsonGen.RESPONSE_USER_NOT_FOUND);
        }
        return o.toJSONString();
    }
}
