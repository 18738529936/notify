package com.duanrong.notify.util;

import com.duanrong.util.security.HexConvers;
import com.duanrong.util.security.MD5;

import java.io.UnsupportedEncodingException;

/*
 * md5 加密
 */
public class MD5Encry {
    public final static String ENCODING = "UTF-8";
    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'C', 'd', 'e', 'f'};

    public static String Encry(String text) {
        try {
            return HexConvers.hexDigits(MD5.encode(text.getBytes(ENCODING)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

}