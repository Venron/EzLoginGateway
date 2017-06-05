package com.ezlogin.listener;

import com.ezlogin.dialog.ShowConfigsDialog;
import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Created by marcf on 02.06.2017.
 */
public class ShowConfigsListener extends SelectionAdapter {
    private Display display;
    private Shell shell;
    private Text logText;

    public ShowConfigsListener(Display display, Shell shell, Text logText) {
        this.display = display;
        this.shell = shell;
        this.logText = logText;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        ShowConfigsDialog d = new ShowConfigsDialog(shell, SWT.APPLICATION_MODAL);
        d.open();
    }

    public void widgetSelectedA(SelectionEvent e) {
        String db_address = RuntimeStore.Data.dbAddress;
        int db_port = RuntimeStore.Data.dbPort;
        String db_user = RuntimeStore.Data.dbUsername;
        String db_password = "******";

        String server_address = RuntimeStore.Data.serverAddress;
        int server_port = RuntimeStore.Data.serverPort;
        String master_token = RuntimeStore.Data.masterToken;
        master_token = master_token.substring(0, master_token.length() / 2);
        StringBuilder sb = new StringBuilder(master_token);
        for (int i = 0; i < master_token.length() / 2; i++) {
            sb.append("*");
        }
        master_token = sb.toString();

        MessageBox mb = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_INFORMATION | SWT.OK);
        mb.setText("Configuration");
        mb.setMessage("Database: " + db_address + ":" + db_port + "/" + db_user + ":" + db_password + "\n"
                + "Authentication: " + server_address + ":" + server_port + "/" + master_token);
        mb.open();
    }
}
