package com.example.chatgpt.util;

public class StringUtils {

    public static boolean isNullOrEmpty(String data) {
        return (null == data || "".equals(data.trim())) ? true : false;
    }
}
