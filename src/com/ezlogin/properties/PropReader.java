package com.ezlogin.properties;

import com.sun.istack.internal.NotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by marcf on 02.06.2017.
 */
public class PropReader {
    private String propFileName;
    private Properties props;

    public PropReader(String propFileName) {
        this.propFileName = propFileName;
    }

    public Properties loadProps() {
        this.props = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(this.propFileName);
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public String getProp(@NotNull String name) {
        return props.getProperty(name);
    }
}
