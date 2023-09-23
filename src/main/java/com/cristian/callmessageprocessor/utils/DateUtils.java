package com.cristian.callmessageprocessor.utils;

public class DateUtils {
    
    public static String formatDate(String date) {
        if ( date != null && date.length() == 8) {
            String year = date.substring(0, 4);
            String month = date.substring(4,6);
            String day = date.substring(6, 8);
            return day + "-" + month + "-" + year;  
        }
        return date; //Return without changes, not valid format
    }
}
