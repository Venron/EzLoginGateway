package com.ezlogin.dialog;

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

        while(!shell.isDisposed()) {
            if(!display.readAndDispatch()) {
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
        localConfigTitleLbl = new Label(shell, SWT.BOLD);
        localConfigTitleLbl.setText("Local configurations:");
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
        localPortTxt.setText("5555");
        localPortTxt.setFont(textFont);
        FormData fdLocalPortTxt = new FormData();
        fdLocalPortTxt.left = new FormAttachment(localPortLbl, 5);
        fdLocalPortTxt.top = new FormAttachment(localConfigTitleLbl, diffTopMargin);
        localPortTxt.setLayoutData(fdLocalPortTxt);
    }
}
