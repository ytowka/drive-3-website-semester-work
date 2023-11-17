package org.danilkha.utils;

import freemarker.template.utility.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public static SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm");


    public static String formatDate(Date date){
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date){
        return timeFormat.format(date);
    }
}
