package ua.notky.notes.tools.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import ua.notky.notes.model.Note;

public class DefaultDataUtil {
    private static final List<Note> NOTES = generateList();

    public static List<Note> getDefaultData(){
        return NOTES;
//        List<Note> list = new ArrayList<>();
//
//        list.add(new Note("Note 1", "Description Note 1 ",
//                getTime(2019, 4, 2, 3, 51, 42)));
//        list.add(new Note("Note 2",
//                "Description Note 2 ",
//                getTime(2020, 3, 3, 4, 52, 43)));
//        list.add(new Note("Note 3", "Description Note 3", new Date()));
//
//        return list;
    }

    public static List<Note> getRange(int startPosition, int sizeRange){
        List<Note> temp = new ArrayList<>();

        for(int i = startPosition; i <= startPosition + sizeRange; i++){
            if(i < NOTES.size()){
                temp.add(NOTES.get(i));
            }
        }

        return temp;
    }

    public static List<Note> generateList(){
        List<Note> list = new ArrayList<>();

        for(int i = 1; i < 3; i++){

            Note note = new Note();
//            note.setId(i);
            note.setTitle("Note " + i);
            note.setDate(new Date());
            note.setDescription("Description " + i);
            list.add(note);
        }

        return list;
    }



    private static Date getTime(int year, int month, int day, int hour, int minute, int second){
        Calendar calendar = new GregorianCalendar(Locale.ENGLISH);
        calendar.set(year, month, day, hour, minute, second);
        return calendar.getTime();
    }
}
