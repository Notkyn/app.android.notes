package ua.notky.notes.service;

import android.content.Context;

import java.util.List;

import ua.notky.notes.model.Note;
import ua.notky.notes.repository.note.NoteRepositoryLite;
import ua.notky.notes.repository.note.NoteRepositoryLiteImp;

import static ua.notky.notes.util.Validation.checkNew;
import static ua.notky.notes.util.Validation.checkNotFoundWithId;
import static ua.notky.notes.util.Validation.checkNotNull;
import static ua.notky.notes.util.Validation.checkUpdated;

public class NoteServiceImp implements NoteService {
    private final NoteRepositoryLite repository;

    public NoteServiceImp(Context context) {
        this.repository = new NoteRepositoryLiteImp(context);
    }


    @Override
    public Note save(Note note) {
        checkNotNull(note);
        checkNew(note);
        return repository.save(note);
    }

    @Override
    public void delete(long id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Note get(long id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public Note update(Note note) {
        checkNotNull(note);
        checkUpdated(note);
        return repository.update(note);
    }

    @Override
    public List<Note> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Note> saveAll(List<Note> list) {
        for(Note note : list){
            note.setId(save(note).getId());
        }
        return list;
    }
}
