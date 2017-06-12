package com.ezlogin.help;

import java.io.UnsupportedEncodingException;

/**
 * Created by marcf on 12.06.2017.
 */
public class Digest {
    public static String convertToSha256(final String md5) {
        StringBuffer sb = null;
        try {
            final java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA256");
            byte[] array = null;
            try {
                array = md.digest(md5.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (final java.security.NoSuchAlgorithmException e) {
        }
        return sb.toString();
    }
}
