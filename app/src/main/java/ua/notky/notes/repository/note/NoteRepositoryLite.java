package ua.notky.notes.repository.note;

import java.util.List;

import ua.notky.notes.model.Note;

public interface NoteRepositoryLite {
    Note save(Note note);
    boolean delete(int id);
    Note get(int id);
    Note update(Note note);
    List<Note> getAll();
}
