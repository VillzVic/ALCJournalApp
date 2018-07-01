package com.vic.villz.journalapp.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {

    public static String getTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:mm:yyyy hh:mm");
        String date = dateFormat.format(calendar.getTime());

        return date;
    }
}
