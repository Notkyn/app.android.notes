package ua.notky.notes.data.service;

import android.content.Context;

import java.util.List;

import ua.notky.notes.data.model.Note;
import ua.notky.notes.data.repository.note.NoteRepositoryLite;
import ua.notky.notes.data.repository.note.NoteRepositoryLiteImp;

import static ua.notky.notes.util.ValidationUtil.checkNotFoundWithId;
import static ua.notky.notes.util.ValidationUtil.checkNotNull;

public class NoteServiceImp implements NoteService {
    private final NoteRepositoryLite repository;

    public NoteServiceImp(Context context) {
        this.repository = new NoteRepositoryLiteImp(context);
    }


    @Override
    public Note save(Note note) {
        checkNotNull(note);
        if(note.isNew()) {
            return repository.save(note);
        } else {
            return repository.update(note);
        }
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
