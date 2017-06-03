package com.ezlogin.log;

import com.sun.istack.internal.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marcf on 02.06.2017.
 */
public class LogStamp {
    private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    private static String getTime() {
        return "[" + sdf.format(new Date()) + "] ";
    }

    public static String getStamp(@NotNull String log) {
        return LogStamp.getTime() + log + "\n";
    }
}
