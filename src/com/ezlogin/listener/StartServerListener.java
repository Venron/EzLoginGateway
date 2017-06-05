package com.ezlogin.listener;

import com.ezlogin.gui.MainGUI;
import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

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
        if(RuntimeStore.Connection.serverSocket != null) {
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
                Display.getDefault().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        MainGUI.externalLog("Port " + RuntimeStore.Data.listenPort + " already in use");
                    }
                });
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

        public ServerThread(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            Display.getDefault().asyncExec(new Runnable() {
                @Override
                public void run() {
                    MainGUI.externalLog("Received conntection from " + client.getInetAddress().toString());
                }
            });
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
