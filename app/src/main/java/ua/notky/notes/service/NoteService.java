package ua.notky.notes.service;

import java.util.List;

import androidx.paging.DataSource;
import ua.notky.notes.model.Note;

public interface NoteService {
    Note save(Note note);
    void delete(Note note);
    Note get(int id);
    List<Note> getAll();
    DataSource.Factory<Integer, Note> getFactoryDataSource();
}
