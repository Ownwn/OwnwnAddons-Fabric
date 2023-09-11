package com.ownwn.util;

public class Utils {
    public static String capitalString(String string) { // capitalise a string
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String capitalString(boolean bool) { // capitalise a string
        String string = String.valueOf(bool);
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

    public static String removeLegacyFormatting(String string) {
        return string.replaceAll("§.", "");
    }
}
