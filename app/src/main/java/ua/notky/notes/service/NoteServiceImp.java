package ua.notky.notes.service;

import java.util.List;

import javax.inject.Inject;

import ua.notky.notes.model.Note;
import ua.notky.notes.dao.note.NoteRepositoryLite;
import ua.notky.notes.util.NoteUtil;
import ua.notky.notes.util.dagger.AppDagger;

import static ua.notky.notes.util.ValidationUtil.checkNotFoundWithId;
import static ua.notky.notes.util.ValidationUtil.checkNotNull;

public class NoteServiceImp implements NoteService {
    @Inject NoteRepositoryLite repository;

    public NoteServiceImp() {
        AppDagger.getInstance().getComponent().injectNoteService(this);
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
    public List<Note> getAllWithSortDate() {
        List<Note> list = getAll();
        NoteUtil.sortWithDate(list);
        return list;
    }

    @Override
    public void saveAll(List<Note> list) {
        for(Note note : list){
            save(note);
        }
    }
}
