package com.ezlogin.storage;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by marcf on 02.06.2017.
 */
public class RuntimeStore {
    public static class Data {
        /*
        * Local application
        * */
        public static int listenPort;

        /*
        * Local application database
        * */
        public static String dbAddress;
        public static int dbPort;
        public static String dbUsername;
        public static String dbPassword;

        /*
        * Authentication application on remote or local server
        * */
        public static String serverAddress;
        public static int serverPort;
        public static String masterToken;
    }
    public static class Connection {
        /*
        * Socket to communicate with the Authentication Server
        * */
        public static ServerSocket serverSocket;
        public static Socket asSocket;
    }
    public static class ActionTypes {
        /*
        * Login
        * */
        public static final String REQUEST_LOGIN = "request_login";
        public static final String RESPONSE_LOGIN = "response_login";
        public static final String REQUEST_CHECK_FOR_USER = "request_check_for_user";
        public static final String RESPONSE_CHECK_FOR_USER = "response_check_for_user";

        /*
        * Register
        * */



        /*
        * Data transfer
        * */
    }
}
