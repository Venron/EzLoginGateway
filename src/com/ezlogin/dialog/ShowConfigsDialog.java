package com.ezlogin.dialog;

import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
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

    private Button okBtn;
    private Button cancelBtn;

    private Color grey = new Color(display, new RGB(160, 160, 160));
    private Color greyLight = new Color(display, new RGB(150, 150, 150));

    public ShowConfigsDialog(Shell shell, int style) {
        super(shell, style);
    }

    public void open() {
        display = getParent().getDisplay();
        shell = new Shell(display, SWT.PRIMARY_MODAL | SWT.SHEET);
        shell.setText("Configurations");
        shell.setLayout(new FormLayout());
        createContents(shell);
        shell.setSize(500, 400);
        Rectangle screenSize = display.getPrimaryMonitor().getBounds();
        shell.setBackground(new Color(display, new RGB(160, 160, 160)));
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
        Font buttonFont = new Font(display, "Segoe UI", 10, SWT.NONE);
        int leftMargin = 15;
        int topMargin = 10;
        int diffTopMargin = 5;
        int diffLeftMargin = 5;

        localConfigTitleLbl = new Label(shell, SWT.BOLD);
        localConfigTitleLbl.setText("Local Configurations");
        localConfigTitleLbl.setFont(textFontBold);
        localConfigTitleLbl.setBackground(grey);
        FormData fdLocalConfigTitleLbl = new FormData();
        fdLocalConfigTitleLbl.left = new FormAttachment(0, leftMargin);
        fdLocalConfigTitleLbl.top = new FormAttachment(0, topMargin);
        localConfigTitleLbl.setLayoutData(fdLocalConfigTitleLbl);

        localPortLbl = new Label(shell, SWT.NONE);
        localPortLbl.setText("Port: ");
        localPortLbl.setFont(textFont);
        localPortLbl.setBackground(grey);
        FormData fdLocalPortLbl = new FormData();
        fdLocalPortLbl.left = new FormAttachment(0, leftMargin);
        fdLocalPortLbl.top = new FormAttachment(localConfigTitleLbl, diffTopMargin);
        localPortLbl.setLayoutData(fdLocalPortLbl);

        localPortTxt = new Text(shell, SWT.NONE);
        localPortTxt.setEditable(false);
        localPortTxt.setText(Integer.toString(RuntimeStore.Data.listenPort));
        localPortTxt.setFont(textFont);
        localPortTxt.setBackground(grey);
        FormData fdLocalPortTxt = new FormData();
        fdLocalPortTxt.left = new FormAttachment(localPortLbl, diffLeftMargin);
        fdLocalPortTxt.top = new FormAttachment(localConfigTitleLbl, diffTopMargin);
        localPortTxt.setLayoutData(fdLocalPortTxt);

        gwDbTitleLbl = new Label(shell, SWT.BOLD);
        gwDbTitleLbl.setText("Gateway Database Configurations");
        gwDbTitleLbl.setFont(textFontBold);
        gwDbTitleLbl.setBackground(grey);
        FormData fdGwDbTitleLabel = new FormData();
        fdGwDbTitleLabel.left = new FormAttachment(0, leftMargin);
        fdGwDbTitleLabel.top = new FormAttachment(localPortLbl, diffTopMargin);
        gwDbTitleLbl.setLayoutData(fdGwDbTitleLabel);

        gwDbAddressLbl = new Label(shell, SWT.NONE);
        gwDbAddressLbl.setText("Address: ");
        gwDbAddressLbl.setFont(textFont);
        gwDbAddressLbl.setBackground(grey);
        FormData fdGwDbAddressLabel = new FormData();
        fdGwDbAddressLabel.left = new FormAttachment(0, leftMargin);
        fdGwDbAddressLabel.top = new FormAttachment(gwDbTitleLbl, diffTopMargin);
        gwDbAddressLbl.setLayoutData(fdGwDbAddressLabel);

        gwDbAddressTxt = new Text(shell, SWT.NONE);
        gwDbAddressTxt.setEditable(false);
        gwDbAddressTxt.setText(RuntimeStore.Data.dbAddress);
        gwDbAddressTxt.setFont(textFont);
        gwDbAddressTxt.setBackground(grey);
        FormData fdGwDbAddressTxt = new FormData();
        fdGwDbAddressTxt.left = new FormAttachment(gwDbAddressLbl, diffLeftMargin);
        fdGwDbAddressTxt.top = new FormAttachment(gwDbTitleLbl, diffTopMargin);
        gwDbAddressTxt.setLayoutData(fdGwDbAddressTxt);

        gwDbPortLbl = new Label(shell, SWT.NONE);
        gwDbPortLbl.setText("Port: ");
        gwDbPortLbl.setFont(textFont);
        gwDbPortLbl.setBackground(grey);
        FormData fdGwDbPortLbl = new FormData();
        fdGwDbPortLbl.left = new FormAttachment(0, leftMargin);
        fdGwDbPortLbl.top = new FormAttachment(gwDbAddressTxt, diffTopMargin);
        gwDbPortLbl.setLayoutData(fdGwDbPortLbl);

        gwDbPortTxt = new Text(shell, SWT.NONE);
        gwDbPortTxt.setEditable(false);
        gwDbPortTxt.setText(Integer.toString(RuntimeStore.Data.dbPort));
        gwDbPortTxt.setFont(textFont);
        gwDbPortTxt.setBackground(grey);
        FormData fdGwDbPortTxt = new FormData();
        fdGwDbPortTxt.left = new FormAttachment(gwDbPortLbl, diffLeftMargin);
        fdGwDbPortTxt.top = new FormAttachment(gwDbAddressTxt, diffTopMargin);
        gwDbPortTxt.setLayoutData(fdGwDbPortTxt);

        gwDbUserLbl = new Label(shell, SWT.NONE);
        gwDbUserLbl.setText("User: ");
        gwDbUserLbl.setFont(textFont);
        gwDbUserLbl.setBackground(grey);
        FormData fdGwDbUserLbl = new FormData();
        fdGwDbUserLbl.left = new FormAttachment(0, leftMargin);
        fdGwDbUserLbl.top = new FormAttachment(gwDbPortLbl, diffTopMargin);
        gwDbUserLbl.setLayoutData(fdGwDbUserLbl);

        gwDbUserTxt = new Text(shell, SWT.NONE);
        gwDbUserTxt.setEditable(false);
        gwDbUserTxt.setText(RuntimeStore.Data.dbUsername);
        gwDbUserTxt.setFont(textFont);
        gwDbUserTxt.setBackground(grey);
        FormData fdGwDbUserTxt = new FormData();
        fdGwDbUserTxt.left = new FormAttachment(gwDbUserLbl, diffLeftMargin);
        fdGwDbUserTxt.top = new FormAttachment(gwDbPortTxt, diffTopMargin);
        gwDbUserTxt.setLayoutData(fdGwDbUserTxt);

        gwDbPasswordLbl = new Label(shell, SWT.NONE);
        gwDbPasswordLbl.setText("Password: ");
        gwDbPasswordLbl.setFont(textFont);
        gwDbPasswordLbl.setBackground(grey);
        FormData fdGwDbPasswordLbl = new FormData();
        fdGwDbPasswordLbl.left = new FormAttachment(0, leftMargin);
        fdGwDbPasswordLbl.top = new FormAttachment(gwDbUserLbl, diffTopMargin);
        gwDbPasswordLbl.setLayoutData(fdGwDbPasswordLbl);

        gwDbPasswordTxt = new Text(shell, SWT.NONE);
        String cc = "";
        for (int i = 0; i < RuntimeStore.Data.dbPassword.length(); i++) {
            cc += "*";
        }
        gwDbPasswordTxt.setText(cc);
        gwDbPasswordTxt.setFont(textFont);
        gwDbPasswordTxt.setEditable(false);
        gwDbPasswordTxt.setBackground(grey);
        FormData fdGwDbPasswordText = new FormData();
        fdGwDbPasswordText.left = new FormAttachment(gwDbPasswordLbl, diffLeftMargin);
        fdGwDbPasswordText.top = new FormAttachment(gwDbUserTxt, diffTopMargin);
        gwDbPasswordTxt.setLayoutData(fdGwDbPasswordText);

        auSvTitleLbl = new Label(shell, SWT.NONE);
        auSvTitleLbl.setText("Authentication Server Configurations");
        auSvTitleLbl.setFont(textFontBold);
        auSvTitleLbl.setBackground(grey);
        FormData fdAuSvTitleLabel = new FormData();
        fdAuSvTitleLabel.left = new FormAttachment(0, leftMargin);
        fdAuSvTitleLabel.top = new FormAttachment(gwDbPasswordLbl, diffTopMargin);
        auSvTitleLbl.setLayoutData(fdAuSvTitleLabel);

        auSvAddressLbl = new Label(shell, SWT.NONE);
        auSvAddressLbl.setText("Address: ");
        auSvAddressLbl.setFont(textFont);
        auSvAddressLbl.setBackground(grey);
        FormData fdAuSvAddressLbl = new FormData();
        fdAuSvAddressLbl.left = new FormAttachment(0, leftMargin);
        fdAuSvAddressLbl.top = new FormAttachment(auSvTitleLbl, diffTopMargin);
        auSvAddressLbl.setLayoutData(fdAuSvAddressLbl);

        auSvAddressTxt = new Text(shell, SWT.NONE);
        auSvAddressTxt.setFont(textFont);
        auSvAddressTxt.setEditable(false);
        auSvAddressTxt.setText(RuntimeStore.Data.serverAddress);
        auSvAddressTxt.setBackground(grey);
        FormData fdAuSvAddressTxt = new FormData();
        fdAuSvAddressTxt.left = new FormAttachment(auSvAddressLbl, diffLeftMargin);
        fdAuSvAddressTxt.top = new FormAttachment(auSvTitleLbl, diffTopMargin);
        auSvAddressTxt.setLayoutData(fdAuSvAddressTxt);

        auSvPortLbl = new Label(shell, SWT.NONE);
        auSvPortLbl.setFont(textFont);
        auSvPortLbl.setText("Port: ");
        auSvPortLbl.setBackground(grey);
        FormData fdAuSvPortLbl = new FormData();
        fdAuSvPortLbl.left = new FormAttachment(0, leftMargin);
        fdAuSvPortLbl.top = new FormAttachment(auSvAddressLbl, diffTopMargin);
        auSvPortLbl.setLayoutData(fdAuSvPortLbl);

        auSvPortTxt = new Text(shell, SWT.NONE);
        auSvPortTxt.setEditable(false);
        auSvPortTxt.setFont(textFont);
        auSvPortTxt.setText(Integer.toString(RuntimeStore.Data.serverPort));
        auSvPortTxt.setBackground(grey);
        FormData fdAuSvPortTxt = new FormData();
        fdAuSvPortTxt.left = new FormAttachment(auSvPortLbl, diffLeftMargin);
        fdAuSvPortTxt.top = new FormAttachment(auSvAddressLbl, diffTopMargin);
        auSvPortTxt.setLayoutData(fdAuSvPortTxt);

        auSvMasterTokenLbl = new Label(shell, SWT.NONE);
        auSvMasterTokenLbl.setFont(textFont);
        auSvMasterTokenLbl.setText("Master Token: ");
        auSvMasterTokenLbl.setBackground(grey);
        FormData fdAuSvMasterTokenLbl = new FormData();
        fdAuSvMasterTokenLbl.left = new FormAttachment(0, leftMargin);
        fdAuSvMasterTokenLbl.top = new FormAttachment(auSvPortLbl, diffTopMargin);
        auSvMasterTokenLbl.setLayoutData(fdAuSvMasterTokenLbl);

        auSvMasterTokenTxt = new Text(shell, SWT.NONE);
        auSvMasterTokenTxt.setEditable(false);
        auSvMasterTokenTxt.setFont(textFont);
        auSvMasterTokenTxt.setText(RuntimeStore.Data.masterToken);
        auSvMasterTokenTxt.setBackground(grey);
        FormData fdAuSvMasterTokenTxt = new FormData();
        fdAuSvMasterTokenTxt.left = new FormAttachment(auSvMasterTokenLbl, diffLeftMargin);
        fdAuSvMasterTokenTxt.top = new FormAttachment(auSvPortTxt, diffTopMargin);
        auSvMasterTokenTxt.setLayoutData(fdAuSvMasterTokenTxt);

        cancelBtn = new Button(shell, SWT.PUSH);
        cancelBtn.setText("Cancel");
        cancelBtn.setBackground(greyLight);
        cancelBtn.setFont(buttonFont);
        FormData fdCancelBtn = new FormData();
        fdCancelBtn.right = new FormAttachment(100, -10);
        fdCancelBtn.bottom = new FormAttachment(100, -10);
        fdCancelBtn.height = 32;
        fdCancelBtn.width = 87;
        cancelBtn.setLayoutData(fdCancelBtn);
        cancelBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }
        });

        okBtn = new Button(shell, SWT.PUSH);
        okBtn.setText("OK");
        okBtn.setBackground(greyLight);
        okBtn.setFont(buttonFont);
        FormData fdOkBtn = new FormData();
        fdOkBtn.right = new FormAttachment(cancelBtn, -5);
        fdOkBtn.bottom = new FormAttachment(100, -10);
        fdOkBtn.height = 32;
        fdOkBtn.width = 87;
        okBtn.setLayoutData(fdOkBtn);
        okBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.dispose();
            }
        });
    }
}
