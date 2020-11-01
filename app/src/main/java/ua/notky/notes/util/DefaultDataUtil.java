package ua.notky.notes.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ua.notky.notes.model.Note;

public class DefaultDataUtil {
    public static List<Note> getDefaulData(){
        List<Note> list = new ArrayList<>();

        list.add(new Note("Note 1", "Description Note 1 ",
                getTime(2019, 4, 2, 4, 50, 43)));
        list.add(new Note("Note 2",
                "Description Note 2 Wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww",
                getTime(2020, 3, 2, 4, 50, 43)));
        list.add(new Note("Note 3  " +
                "titletitletitletitletitletitletitle", "Description Note 3",
                getTime(2020, 2, 2, 4, 50, 43)));
        list.add(new Note("Note 4 titletitletitletitletitletitletitleitletitleitletitle",
                "Description Note 4 DescriptionDescriptionDescriptionDescriptionDescriptionDescriptionDescription",
                new Date()));
        list.add(new Note("Note 5", "Description Note 5", new Date()));
        list.add(new Note("Note 6", "Description Note 6",
                getTime(2020, 5, 2, 4, 50, 43)));
        list.add(new Note("Note 7", "Description Note 7", new Date()));
        list.add(new Note("Note 8", "Description Note 8", new Date()));
        list.add(new Note("Note 9", "Description Note 9", new Date()));
        list.add(new Note("Note 10", "Description Note 10",
                getTime(2020, 11, 1, 5, 31, 3)));

        return list;
    }

    private static Date getTime(int year, int month, int day, int hour, int minute, int second){
        Calendar calendar = new GregorianCalendar(Locale.ENGLISH);
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }
}
