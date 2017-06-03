package com.ezlogin.storage;

import java.net.ServerSocket;

/**
 * Created by marcf on 02.06.2017.
 */
public class RuntimeStore {
    public static class Data {
        public static int listenPort;
        public static String dbAddress;
        public static int dbPort;
        public static String dbUsername;
        public static String dbPassword;
        public static String serverAddress;
        public static int serverPort;
        public static String masterToken;
    }
    public static class Connection {
        public static ServerSocket serverSocket;
    }
}
