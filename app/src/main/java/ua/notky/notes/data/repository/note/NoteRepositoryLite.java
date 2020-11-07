package ua.notky.notes.data.repository.note;

import java.util.List;

import ua.notky.notes.data.model.Note;

public interface NoteRepositoryLite {
    Note save(Note note);
    boolean delete(int id);
    Note get(int id);
    Note update(Note note);
    List<Note> getAll();
}
