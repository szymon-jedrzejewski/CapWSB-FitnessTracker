package com.capgemini.wsb.fitnesstracker.training.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParserUtil {
    public static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+00:00");
        try {
            return isoFormat.parse(dateStr);
        } catch (ParseException e) {
            isoFormat = new SimpleDateFormat("yyyy-MM-dd");
            return isoFormat.parse(dateStr);
        }
    }
}
