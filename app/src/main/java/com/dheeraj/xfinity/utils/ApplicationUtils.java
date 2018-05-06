package com.dheeraj.xfinity.utils;

public class ApplicationUtils {

    public static String getTitle(String text){
        if(text != null){
            String[] splits = text.split("-");
            if(splits.length > 0)
                return splits[0];
        }
        return "";
    }

    public static String getDescription(String text){
        if(text != null){
            String[] splits = text.split("-");
            if(splits.length > 1)
                return splits[1];
        }
        return "";
    }
}
