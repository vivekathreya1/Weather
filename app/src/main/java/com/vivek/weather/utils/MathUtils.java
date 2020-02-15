package com.vivek.weather.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MathUtils {

    public static String getDateFromInt(long dateTimeInMillis){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(dateTimeInMillis * 1000);
        return sdf.format(c.getTime());


    }
}
