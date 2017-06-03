package com.ezlogin.listener;

import com.ezlogin.gui.MainGUI;
import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.omg.SendingContext.RunTime;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by marcf on 03.06.2017.
 */
public class StopServerListener extends SelectionAdapter {
    private Shell shell;
    public StopServerListener(Shell shell) {
        this.shell = shell;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        ServerSocket serverSocket = RuntimeStore.Connection.serverSocket;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            RuntimeStore.Connection.serverSocket = null;
            MessageBox mb = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_INFORMATION | SWT.OK);
            mb.setText("Server stopped");
            mb.setMessage("Server has been stopped!");
            mb.open();
        } else {
            MessageBox mb = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR | SWT.OK);
            mb.setText("Server not running");
            mb.setMessage("No running server found.");
            mb.open();
        }
    }
}
