package ua.notky.notes.service;

import java.util.List;

import ua.notky.notes.model.Note;

public interface NoteService {
    Note save(Note note);
    void delete(long id);
    Note get(long id);
    Note update(Note note);
    List<Note> getAll();
    List<Note> saveAll(List<Note> list);
}
