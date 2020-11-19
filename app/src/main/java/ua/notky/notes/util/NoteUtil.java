package ua.notky.notes.util;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ua.notky.notes.R;
import ua.notky.notes.data.model.Note;

public class NoteUtil {
    public static final String TITLE = "Новая заметка";

    public static void sortWithDate(List<Note> list){
        Collections.sort(list, (note, t1) -> t1.getDate().compareTo(note.getDate()));
    }

    public static Note getDefaultNote(){
        Note note = new Note();
        note.setTitle(TITLE);
        note.setDescription("");
        note.setDate(new Date());
        return note;
    }
}
