package ua.notky.notes.repository.note;

import java.util.List;

import ua.notky.notes.model.Note;

public interface NoteRepositoryLite {
    Note save(Note note);
    boolean delete(long id);
    Note get(long id);
    Note update(Note note);
    List<Note> getAll();
}
