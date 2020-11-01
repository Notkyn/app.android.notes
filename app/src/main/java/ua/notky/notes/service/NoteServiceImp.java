package ua.notky.notes.service;

import android.content.Context;

import java.util.List;

import ua.notky.notes.model.Note;
import ua.notky.notes.repository.note.NoteRepositoryLite;
import ua.notky.notes.repository.note.NoteRepositoryLiteImp;

import static ua.notky.notes.util.ValidationUtil.checkNew;
import static ua.notky.notes.util.ValidationUtil.checkNotFoundWithId;
import static ua.notky.notes.util.ValidationUtil.checkNotNull;
import static ua.notky.notes.util.ValidationUtil.checkUpdated;

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
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Note get(int id) {
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
    public void saveAll(List<Note> list) {
        for(Note note : list){
            note.setId(save(note).getId());
        }
    }
}
