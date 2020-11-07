package ua.notky.notes.data.repository.note;

import java.util.List;

import ua.notky.notes.data.model.Note;

public interface NoteRepositoryLite {
    void save(Note note);
    boolean delete(int id);
    Note get(int id);
    void update(Note note);
    List<Note> getAll();
}
