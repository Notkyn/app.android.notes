package ua.notky.notes.tools.utils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import ua.notky.notes.model.Note;

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

    public static boolean equalsId(Note first, Note second){
        return first.getId().equals(second.getId());
    }

    public static boolean equalsWithoutId(Note first, Note second){
        return first.getTitle().equals(second.getTitle())
                && first.getDescription().equals(second.getDescription())
                && first.getDate().equals(second.getDate());
    }
}
