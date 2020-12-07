package ua.notky.notes.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ua.notky.notes.model.Note;

public class DefaultDataUtil {
    public static List<Note> getDefaultData(){
        List<Note> list = new ArrayList<>();

        list.add(new Note("Note 1", "Description Note 1 ",
                getTime(2019, 4, 2, 3, 51, 42)));
        list.add(new Note("Note 2",
                "Description Note 2 ",
                getTime(2020, 3, 3, 4, 52, 43)));
        list.add(new Note("Note 3", "Description Note 3", new Date()));

        return list;
    }

    private static Date getTime(int year, int month, int day, int hour, int minute, int second){
        Calendar calendar = new GregorianCalendar(Locale.ENGLISH);
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }
}
