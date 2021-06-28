package com.example.note.util;


import android.content.res.Resources;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private static Resources res;

    public static void setResources(Resources resources) {
        res = resources;
    }

    public static Resources getResources() {
        return res;
    }

    public static String getString(int resourceId, Object... params) {
        return String.format(res.getString(resourceId), params);
    }
    
    public static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }


    public static String format(String template, Map<String, String> variables) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}");
        Matcher matcher = pattern.matcher(template);
        // StringBuilder cannot be used here because Matcher expects
        // StringBuffer
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            if (variables.containsKey(matcher.group(1))) {
                String replacement = variables.get(matcher.group(1));
                // quote to work properly with $ and {,} signs
                matcher.appendReplacement(
                        buffer,
                        replacement != null ? Matcher
                                .quoteReplacement(replacement) : "");
            }
        }
        matcher.appendTail(buffer);
        String bufferString = buffer.toString();
        //bufferString = bufferString.replace("'","''");
        return bufferString;
    }

}
