package com.ezlogin.listener;

import com.ezlogin.gateway.ClientConnection;
import com.ezlogin.gui.MainGUI;
import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import sun.plugin2.message.Message;

import java.io.IOException;
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
        if (RuntimeStore.Connection.serverSocket != null) { /*Check if the server has been started before*/
            MessageBox mb = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR | SWT.OK);
            mb.setText("Server already running");
            mb.setMessage("Server is already running!");
            mb.open();
            return;
        }
        ServerListener serverListener = new ServerListener(RuntimeStore.Data.listenPort);
        serverListener.start();
    }

    private void logToUiThread(String msg) {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                MainGUI.externalLog(msg);
            }
        });
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

                /*
                * Connect to authentication server
                * */
                connectToAuthenticationServer();
                Display.getDefault().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        MainGUI.externalLog("Connected to Authentication Server.");
                        MessageBox mb = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_INFORMATION | SWT.OK);
                        mb.setText("Authentication Server Connection");
                        mb.setMessage("Connected to Authentication Server");
                        mb.open();
                    }
                });

                serverSocket = new ServerSocket(RuntimeStore.Data.listenPort);
                RuntimeStore.Connection.serverSocket = serverSocket; /*Save ServerSocket*/
                while (true) {
                    Socket client = serverSocket.accept();
                    ClientConnection clientConnection = new ClientConnection(client);
                    clientConnection.start();
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
            System.out.println("Connected to Authentication Server. (" + RuntimeStore.Connection.asSocket.getInetAddress() + ":" + RuntimeStore.Connection.asSocket.getPort());
        }

        /*
        * Open a socket to the authentication server that can be used for later communication
        * */
        private void connectToAuthenticationServer() {
            String asAddress = RuntimeStore.Data.serverAddress;
            int asPort = RuntimeStore.Data.serverPort;
            String masterToken = RuntimeStore.Data.masterToken;
            if (asAddress.isEmpty() || asPort == 0 || masterToken.isEmpty()) {
                MainGUI.externalLog("Could not connect to Authentication Server. Check the config.properties and try again.");
            }
            Socket asSocket = null;
            try {
                asSocket = new Socket(asAddress, asPort);
                System.out.println("Connected to the Authentication Server on " + asAddress + ":" + asPort);
            } catch (IOException e) {
                MainGUI.externalLog("Error raised when connecting to Authentication Server (" + asAddress + ")");
            }
            if (asSocket != null) {
                RuntimeStore.Connection.asSocket = asSocket;
            }
            System.out.println("asSocket saved to the RuntimeStore.");
        }
    }
}
