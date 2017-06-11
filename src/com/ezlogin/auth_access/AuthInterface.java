package com.ezlogin.auth_access;

import com.ezlogin.gui.MainGUI;
import com.ezlogin.storage.RuntimeStore;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by marcf on 11.06.2017.
 */
public class AuthInterface {

    public synchronized static String sendRequestAndWaitForResponse(String request) {
        Socket asSocket = null;
        if (getSocket() != null) {
            asSocket = getSocket();
        }
        try (PrintWriter out = new PrintWriter(asSocket.getOutputStream(), true)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(asSocket.getInputStream()));
            out.println(request); /*Send the request*/
            String response = in.readLine(); /*receive the response*/
            return response;

            JSONParser p = new JSONParser();
            JSONObject o = (JSONObject) p.parse(request);

            /*
            * Evaluate the response
            * */
            if (o.get("action").equals(RuntimeStore.ActionTypes.RESPONSE_CHECK_FOR_USER)) {
                String email = (String) o.get("email");
                String response1 = (String) o.get("response");
                MainGUI.externalLog("User '" + email + "':" + response1);
            }

        } catch (IOException e) {
            System.out.println("Catched IOException at sendRequestAndWait");
        } catch (ParseException e) {
            System.out.println("ParseException when parsing the response at sendRequestAndWait");
        }
    }

    private static Socket getSocket() {
        if (RuntimeStore.Connection.asSocket != null) {
            return RuntimeStore.Connection.asSocket;
        } else {
            return null;
        }
    }
}
