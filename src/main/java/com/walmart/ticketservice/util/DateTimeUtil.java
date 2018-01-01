package com.walmart.ticketservice.util;

import java.sql.Timestamp;
import java.util.Calendar;

public class DateTimeUtil {
    public static Timestamp getSQLDateTime(String dateTime) {
        return Timestamp.valueOf(dateTime);
    }

    public static String getDateTimeAsString(Timestamp timeStamp) {
        return timeStamp.toString();
    }

    public static Timestamp getCurrentSQLTime() {
        Calendar calendar = Calendar.getInstance();
        return new java.sql.Timestamp(calendar.getTime().getTime());
    }


}
