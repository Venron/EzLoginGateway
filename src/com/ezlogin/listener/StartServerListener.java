package com.ezlogin.listener;

import com.ezlogin.gui.MainGUI;
import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by marcf on 02.06.2017.
 */
public class StartServerListener extends SelectionAdapter {
    private Display display;
    private Shell shell;

    public StartServerListener(Display display, Shell shell) {
        this.display = display;
        this.shell = shell;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        if (RuntimeStore.Connection.serverSocket != null) {
            MessageBox mb = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR | SWT.OK);
            mb.setText("Server already running");
            mb.setMessage("Server is already running!");
            mb.open();
            return;
        }
        ServerListener serverListener = new ServerListener(RuntimeStore.Data.listenPort);
        serverListener.start();
    }

    /*Inner class that listens for new connections*/
    private class ServerListener extends Thread {
        private int serverPort;

        public ServerListener(int serverPort) {
            this.serverPort = serverPort;
        }

        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                Display.getDefault().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        MainGUI.externalLog("Starting on port " + RuntimeStore.Data.listenPort);
                        MessageBox mb = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_INFORMATION | SWT.OK);
                        mb.setText("Server started");
                        mb.setMessage("Server has been started!");
                        mb.open();
                    }
                });
                serverSocket = new ServerSocket(RuntimeStore.Data.listenPort);
                RuntimeStore.Connection.serverSocket = serverSocket; /*Save ServerSocket*/
                while (true) {
                    Socket client = serverSocket.accept();
                    ServerThread serverThread = new ServerThread(client);
                    serverThread.start();
                }
            } catch (BindException e) {
                logToUiThread("Port " + RuntimeStore.Data.listenPort + " already in use.");
            } catch (SocketException e) {
                System.out.println("Socket has been closed. Cannot receive connections anymore");
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                if (serverSocket != null) {
                    try {
                        serverSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /*Inner handler class for incoming connections*/
    private class ServerThread extends Thread {
        private Socket client;
        private PrintWriter out;
        private BufferedReader in;

        public ServerThread(Socket client) {
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
                    if(o == null) {
                        logToUiThread("Error at parsing client JSON request (" + client.getInetAddress() + ")");
                    }
                    String action = (String) o.get("action");
                    /*Evaluate the received request from the client*/

                    /*
                    * Login request from the Client
                    * */
                    if(action.equals(RuntimeStore.ActionTypes.REQUEST_LOGIN)) {
                        String email = (String) o.get("email");
                        logToUiThread("Sending login request for user '" + email + "'");
                        /*Send request_check_for_user JSON request to the AS*/
                    }

                }
            } catch (IOException e) {
                logToUiThread("Error occured at reading data from client (" + client.getInetAddress() + ")");
            }

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
