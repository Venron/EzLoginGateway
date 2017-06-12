package com.ezlogin.gateway;

import com.ezlogin.gui.MainGUI;
import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.widgets.Display;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by marcf on 12.06.2017.
 */
    /*Inner handler class for incoming connections*/
// TODO: use try with resources on the stream objects
public class ClientConnection extends Thread {
    private Socket client;
    private PrintWriter out;
    private BufferedReader in;

    public ClientConnection(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
            /*Get the streams*/
        try {
            out = new PrintWriter(client.getOutputStream(), true);
        } catch (IOException e) {
            logToUiThread("Failed to receive data from client (Exception at PrintWriter). (" + client.getInetAddress() + ")");
            return; /*Stop thread execution*/
        }
        try {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            logToUiThread("Failed to receive data from client (Exception at BufferedReader). (" + client.getInetAddress() + ")");
            return; /*Stop thread execution*/
        }

        logToUiThread("New connection (" + client.getInetAddress().toString() + ")");

        String request = "";

        try {
            while ((request = in.readLine()) != null) {
                    /*Parse the JSON message from the client*/
                JSONParser p = new JSONParser();
                JSONObject o = null;
                try {
                    o = (JSONObject) p.parse(request);
                } catch (ParseException e) {
                    Display.getDefault().asyncExec(new Runnable() {
                        @Override
                        public void run() {
                            MainGUI.externalLog("Error at parsing request from client (" + client.getInetAddress() + ")");
                        }
                    });
                }
                if (o == null) {
                    logToUiThread("Error at parsing client JSON request (" + client.getInetAddress() + ")");
                }
                String action = (String) o.get("action");
                    /*Evaluate the received request from the client*/

                    /*
                    * Login request from the Client
                    * */
                if (action.equals(RuntimeStore.ActionTypes.REQUEST_LOGIN)) {
                    String email = (String) o.get("email");
                    String hash = (String) o.get("hash");
                    logToUiThread("Sending login request for user '" + email + "'");
                    /*Send request_check_for_user JSON request to the AS*/
                    String loginRequest = JsonGen.getJsonRequestUserValidation(request, email, hash, RuntimeStore.Data.masterToken);
                    
                }
            }
        } catch (IOException e) {
            logToUiThread("Error occured at reading data from client (" + client.getInetAddress() + ")");
        } catch (Exception e) {
            logToUiThread("General Exception in ClientConnection");
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void logToUiThread(String msg) {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                MainGUI.externalLog(msg);
            }
        });
    }
}