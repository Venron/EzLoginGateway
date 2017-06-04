package com.ezlogin.dialog;

import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;

/**
 * Created by marcf on 03.06.2017.
 */
public class ShowConfigsDialog extends Dialog {
    private Display display;
    private Shell shell;

    private Label localConfigTitleLbl;
    private Label localPortLbl;
    private Label gwDbTitleLbl;
    private Label gwDbAddressLbl;
    private Label gwDbPortLbl;
    private Label gwDbUserLbl;
    private Label gwDbPasswordLbl;
    private Label auSvTitleLbl;
    private Label auSvAddressLbl;
    private Label auSvPortLbl;
    private Label auSvMasterTokenLbl;

    private Text localPortTxt;
    private Text gwDbAddressTxt;
    private Text gwDbPortTxt;
    private Text gwDbUserTxt;
    private Text gwDbPasswordTxt;
    private Text auSvAddressTxt;
    private Text auSvPortTxt;
    private Text auSvMasterTokenTxt;


    public ShowConfigsDialog(Shell shell, int style) {
        super(shell, style);
    }

    public void open() {
        display = getParent().getDisplay();
        shell = new Shell(display, SWT.SHELL_TRIM);
        shell.setText("Configurations");
        shell.setLayout(new FormLayout());
        createContents(shell);
        shell.setSize(400, 650);
        Rectangle screenSize = display.getPrimaryMonitor().getBounds();
        shell.setLocation((screenSize.width - shell.getBounds().width) / 2,
                (screenSize.height - shell.getBounds().height) / 2);
        shell.open();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    private void createContents(Shell shell) {
        shell.forceFocus();
        Font textFont = new Font(display, "Segoe UI", 12, SWT.NONE);
        Font textFontBold = new Font(display, "Segoe UI", 12, SWT.BOLD);
        int leftMargin = 15;
        int topMargin = 10;
        int diffTopMargin = 5;
        int diffLeftMargin = 5;

        localConfigTitleLbl = new Label(shell, SWT.BOLD);
        localConfigTitleLbl.setText("Local Configurations");
        localConfigTitleLbl.setFont(textFontBold);
        FormData fdLocalConfigTitleLbl = new FormData();
        fdLocalConfigTitleLbl.left = new FormAttachment(0, leftMargin);
        fdLocalConfigTitleLbl.top = new FormAttachment(0, topMargin);
        localConfigTitleLbl.setLayoutData(fdLocalConfigTitleLbl);

        localPortLbl = new Label(shell, SWT.NONE);
        localPortLbl.setText("Port: ");
        localPortLbl.setFont(textFont);
        FormData fdLocalPortLbl = new FormData();
        fdLocalPortLbl.left = new FormAttachment(0, leftMargin);
        fdLocalPortLbl.top = new FormAttachment(localConfigTitleLbl, diffTopMargin);
        localPortLbl.setLayoutData(fdLocalPortLbl);

        localPortTxt = new Text(shell, SWT.NONE);
        localPortTxt.setEditable(false);
        localPortTxt.setText(Integer.toString(RuntimeStore.Data.serverPort));
        localPortTxt.setFont(textFont);
        FormData fdLocalPortTxt = new FormData();
        fdLocalPortTxt.left = new FormAttachment(localPortLbl, diffLeftMargin);
        fdLocalPortTxt.top = new FormAttachment(localConfigTitleLbl, diffTopMargin);
        localPortTxt.setLayoutData(fdLocalPortTxt);

        gwDbTitleLbl = new Label(shell, SWT.BOLD);
        gwDbTitleLbl.setText("Gateway Database Configurations");
        gwDbTitleLbl.setFont(textFontBold);
        FormData fdGwDbTitleLabel = new FormData();
        fdGwDbTitleLabel.left = new FormAttachment(0, leftMargin);
        fdGwDbTitleLabel.top = new FormAttachment(localPortLbl, diffTopMargin);
        gwDbTitleLbl.setLayoutData(fdGwDbTitleLabel);

        gwDbAddressLbl = new Label(shell, SWT.NONE);
        gwDbAddressLbl.setText("Address: ");
        gwDbAddressLbl.setFont(textFont);
        FormData fdGwDbAddressLabel = new FormData();
        fdGwDbAddressLabel.left = new FormAttachment(0, leftMargin);
        fdGwDbAddressLabel.top = new FormAttachment(gwDbTitleLbl, diffTopMargin);
        gwDbAddressLbl.setLayoutData(fdGwDbAddressLabel);

        gwDbAddressTxt = new Text(shell, SWT.NONE);
        gwDbAddressTxt.setEditable(false);
        gwDbAddressTxt.setText(RuntimeStore.Data.dbAddress);
        gwDbAddressTxt.setFont(textFont);
        FormData fdGwDbAddressTxt = new FormData();
        fdGwDbAddressTxt.left = new FormAttachment(gwDbAddressLbl, diffLeftMargin);
        fdGwDbAddressTxt.top = new FormAttachment(gwDbTitleLbl, diffTopMargin);
        gwDbAddressTxt.setLayoutData(fdGwDbAddressTxt);

        gwDbPortLbl = new Label(shell, SWT.NONE);
        gwDbPortLbl.setText("Port: ");
        gwDbPortLbl.setFont(textFont);
        FormData fdGwDbPortLbl = new FormData();
        fdGwDbPortLbl.left = new FormAttachment(0, leftMargin);
        fdGwDbPortLbl.top = new FormAttachment(gwDbAddressTxt, diffTopMargin);
        gwDbPortLbl.setLayoutData(fdGwDbPortLbl);

        gwDbPortTxt = new Text(shell, SWT.NONE);
        gwDbPortTxt.setEditable(false);
        gwDbPortTxt.setText(Integer.toString(RuntimeStore.Data.dbPort));
        gwDbPortTxt.setFont(textFont);
        FormData fdGwDbPortTxt = new FormData();
        fdGwDbPortTxt.left = new FormAttachment(gwDbPortLbl, diffLeftMargin);
        fdGwDbPortTxt.top = new FormAttachment(gwDbAddressTxt, diffTopMargin);
        gwDbPortTxt.setLayoutData(fdGwDbPortTxt);

        gwDbUserLbl = new Label(shell, SWT.NONE);
        gwDbUserLbl.setText("User: ");
        gwDbUserLbl.setFont(textFont);
        FormData fdGwDbUserLbl = new FormData();
        fdGwDbUserLbl.left = new FormAttachment(0, leftMargin);
        fdGwDbUserLbl.top = new FormAttachment(gwDbPortLbl, diffTopMargin);
        gwDbUserLbl.setLayoutData(fdGwDbUserLbl);

        gwDbUserTxt = new Text(shell, SWT.NONE);
        gwDbUserTxt.setEditable(false);
        gwDbUserTxt.setText(RuntimeStore.Data.dbUsername);
        gwDbUserTxt.setFont(textFont);
        FormData fdGwDbUserTxt = new FormData();
        fdGwDbUserTxt.left = new FormAttachment(gwDbUserLbl, diffLeftMargin);
        fdGwDbUserTxt.top = new FormAttachment(gwDbPortTxt, diffTopMargin);
        gwDbUserTxt.setLayoutData(fdGwDbUserTxt);

        gwDbPasswordLbl = new Label(shell, SWT.NONE);
        gwDbPasswordLbl.setText("Password: ");
        gwDbPasswordLbl.setFont(textFont);
        FormData fdGwDbPasswordLbl = new FormData();
        fdGwDbPasswordLbl.left = new FormAttachment(0, leftMargin);
        fdGwDbPasswordLbl.top = new FormAttachment(gwDbUserLbl, diffTopMargin);
        gwDbPasswordLbl.setLayoutData(fdGwDbPasswordLbl);

        gwDbPasswordTxt = new Text(shell, SWT.NONE);
        String cc = "";
        for(int i = 0; i < RuntimeStore.Data.dbPassword.length(); i++) {
            cc += "*";
        }
        gwDbPasswordTxt.setText(cc);
        gwDbPasswordTxt.setFont(textFont);
        gwDbPasswordTxt.setEditable(false);
        FormData fdGwDbPasswordText = new FormData();
        fdGwDbPasswordText.left = new FormAttachment(gwDbPasswordLbl, diffLeftMargin);
        fdGwDbPasswordText.top = new FormAttachment(gwDbUserTxt, diffTopMargin);
        gwDbPasswordTxt.setLayoutData(fdGwDbPasswordText);
    }
}
