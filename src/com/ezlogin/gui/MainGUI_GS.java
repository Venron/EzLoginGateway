package com.ezlogin.gui;

import com.ezlogin.listener.CleanupListener;
import com.ezlogin.listener.ShowConfigsListener;
import com.ezlogin.listener.StartServerListener;
import com.ezlogin.listener.StopServerListener;
import com.ezlogin.log.LogStamp;
import com.ezlogin.properties.PropReader;
import com.ezlogin.storage.RuntimeStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.util.Properties;

/**
 * Created by marcf on 01.06.2017.
 */
public class MainGUI_GS {
    // GUI
    private Display display;
    private Shell shell;
    private static Text logText;
    private Button showConfigsBtn;
    private static Properties props;
    public static Button startServerBtn;
    public static Button stopServerBtn;

    // Other
    private Font textFont;

    public MainGUI_GS() {
        createDisplay();
        createShell();
        createFonts(display);
        createLogWindow();
        createActionButtons();
        createListener();
        startup();
    }

    private void createDisplay() {
        this.display = new Display();
    }

    private void createShell() {
        this.shell = new Shell(display);
        shell.setText("EzLogin Gateway Server");
        shell.setSize(new Point(800, 600));
        Rectangle screenSize = display.getPrimaryMonitor().getBounds();
        shell.setLocation((screenSize.width - shell.getBounds().width) / 2,
                (screenSize.height - shell.getBounds().height) / 2);
        shell.setBackground(new Color(display, new RGB(160, 160, 160)));
        shell.setLayout(new FormLayout());
    }

    public void start() {
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    private void createLogWindow() {
        this.logText = new Text(shell, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
        logText.setEditable(false);
        logText.setFont(new Font(display, "Segoe UI", 10, SWT.NONE));
        FormData fdLogText = new FormData();
        fdLogText.top = new FormAttachment(0, 15);
        fdLogText.left = new FormAttachment(0, 15);
        fdLogText.height = 350;
        fdLogText.width = 725;
        logText.setLayoutData(fdLogText);
    }

    private void fill() {
        for (int i = 0; i < 40; i++) {
            logText.append(LogStamp.getStamp("Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet " + i));
        }
    }

    private void createActionButtons() {
        showConfigsBtn = new Button(shell, SWT.PUSH);
        showConfigsBtn.setText("Details...");
        showConfigsBtn.setBackground(new Color(display, new RGB(150, 150, 150)));
        showConfigsBtn.setFont(textFont);
        FormData fdLoadConfigsBtn = new FormData();
        fdLoadConfigsBtn.left = new FormAttachment(0, 15);
        fdLoadConfigsBtn.top = new FormAttachment(logText, 5);
        fdLoadConfigsBtn.height = 32;
        fdLoadConfigsBtn.width = 87;
        showConfigsBtn.setLayoutData(fdLoadConfigsBtn);

        startServerBtn = new Button(shell, SWT.PUSH);
        startServerBtn.setText("Start");
        startServerBtn.setBackground(new Color(display, new RGB(150, 150, 150)));
        startServerBtn.setFont(textFont);
        FormData fdStartServerBtn = new FormData();
        fdStartServerBtn.left = new FormAttachment(showConfigsBtn, 15);
        fdStartServerBtn.top = new FormAttachment(logText, 5);
        fdStartServerBtn.height = 32;
        fdStartServerBtn.width = 87;
        startServerBtn.setLayoutData(fdStartServerBtn);

        stopServerBtn = new Button(shell, SWT.PUSH);
        stopServerBtn.setText("Stop");
        stopServerBtn.setBackground(new Color(display, new RGB(150, 150, 150)));
        stopServerBtn.setFont(textFont);
        FormData fdStopServerBtn = new FormData();
        fdStopServerBtn.left = new FormAttachment(startServerBtn, 5);
        fdStopServerBtn.top = new FormAttachment(logText, 5);
        fdStopServerBtn.height = 32;
        fdStopServerBtn.width = 87;
        stopServerBtn.setLayoutData(fdStopServerBtn);
    }

    private void createListener() {
        showConfigsBtn.addSelectionListener(new ShowConfigsListener(display, shell, logText));
        startServerBtn.addSelectionListener(new StartServerListener(display, shell));
        stopServerBtn.addSelectionListener(new StopServerListener(shell));
        shell.addDisposeListener(new CleanupListener());
    }

    private void startup() {
        PropReader propReader = new PropReader("config.properties");
        MainGUI_GS.props = propReader.loadProps();
        try {
            RuntimeStore.Data.listenPort = Integer.parseInt(MainGUI_GS.props.getProperty("port")); /*Listen port property*/
        } catch (NumberFormatException e) {
            RuntimeStore.Data.listenPort = 3434; /*If the port was e.g. a string use the default port 3434*/
            System.out.println("Invalid listener port. Using default port 3434");
        }
        RuntimeStore.Data.dbAddress = MainGUI_GS.props.getProperty("db-address");
        try {
            RuntimeStore.Data.dbPort = Integer.parseInt(MainGUI_GS.props.getProperty("db-port")); /*DB port property*/
        } catch (NumberFormatException e) {
            RuntimeStore.Data.dbPort = 5432;
            System.out.println("Invalid database port. Using default port 5432");
        }
        RuntimeStore.Data.dbUsername = MainGUI_GS.props.getProperty("db-user");
        RuntimeStore.Data.dbPassword = MainGUI_GS.props.getProperty("db-password");
        RuntimeStore.Data.serverAddress = MainGUI_GS.props.getProperty("server-address");
        try {
            RuntimeStore.Data.serverPort = Integer.parseInt(MainGUI_GS.props.getProperty("server-port"));
        } catch (NumberFormatException e) {
            RuntimeStore.Data.serverPort = 3435;
            System.out.println("Invalid authentication server port. Using default port 3435");
        }
        RuntimeStore.Data.masterToken = MainGUI_GS.props.getProperty("master-token");
        System.out.println("Startup finished");
        logText.append(LogStamp.getStamp("Startup finished"));
    }

    public static void externalLog(String log) {
        logText.append(LogStamp.getStamp(log));
    }

    private void createFonts(Display display) {
        textFont = new Font(display, "Segoe UI", 10, SWT.NONE);
    }
}
