package ua.notky.notes.util;

import java.util.Collections;
import java.util.List;

import ua.notky.notes.model.Note;

public class NoteUtil {
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";

    public static void sortWithDate(List<Note> list){
        Collections.sort(list, (note, t1) -> t1.getDate().compareTo(note.getDate()));
    }
}
