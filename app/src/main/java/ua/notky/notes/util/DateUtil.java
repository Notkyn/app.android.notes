package ua.notky.notes.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtil {
    private static final String TIME_FORMAT = "hh:mm";
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    public static String format(Date date){
        return new SimpleDateFormat(getPattern(date), Locale.ENGLISH).format(date);
    }

    private static String getPattern(Date date){
        Calendar now = new GregorianCalendar(Locale.ENGLISH);
        now.setTime(new Date());

        Calendar value = new GregorianCalendar();
        value.setTime(date);

        if(now.get(Calendar.YEAR) == value.get(Calendar.YEAR)
                && now.get(Calendar.MONTH) == value.get(Calendar.MONTH)
                && now.get(Calendar.DAY_OF_MONTH) == value.get(Calendar.DAY_OF_MONTH)) {
            return TIME_FORMAT;
        } else {
            return DATE_FORMAT;
        }
    }
}
