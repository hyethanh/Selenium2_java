package com.auto.utils;

public class StringUtils {

    public static String replaceSpaceCharWithNBSP(String string) {
        return string.replace(" ", "\u00A0");
    }
}
