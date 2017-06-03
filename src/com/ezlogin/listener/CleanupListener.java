package com.ezlogin.listener;

import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by marcf on 03.06.2017.
 */
public class CleanupListener implements DisposeListener {
    public CleanupListener() {

    }

    @Override
    public void widgetDisposed(DisposeEvent e) {
        ServerSocket serverSocket = RuntimeStore.Connection.serverSocket;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
