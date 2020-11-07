package ua.notky.notes.data.service;

import java.util.List;

import ua.notky.notes.data.model.Note;

public interface NoteService {
    void save(Note note);
    void delete(int id);
    Note get(int id);
    List<Note> getAll();
    void saveAll(List<Note> list);
}
