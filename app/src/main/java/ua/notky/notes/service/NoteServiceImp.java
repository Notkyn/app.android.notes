package ua.notky.notes.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.paging.DataSource;
import ua.notky.notes.dao.room.NoteDao;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.utils.NoteUtil;
import ua.notky.notes.tools.dagger.AppDagger;

import static ua.notky.notes.tools.utils.ValidationUtil.checkNotFoundWithId;
import static ua.notky.notes.tools.utils.ValidationUtil.checkNotFoundWithIdForDelete;
import static ua.notky.notes.tools.utils.ValidationUtil.checkNotNull;

public class NoteServiceImp implements NoteService {
//    Using SqLite
//    @Inject NoteRepositoryLite repository;

//    Using Room
    @Inject NoteDao repository;

    public NoteServiceImp() {
        AppDagger.getInstance().getComponent().injectNoteService(this);
    }

    @Override
    public Note save(Note note) {
        checkNotNull(note);
        if(note.isNew()) {
            note.setId((int) repository.save(note));
        } else {
            repository.update(note);
        }
        return note;
    }

    @Override
    public void delete(Note note) {
        checkNotFoundWithIdForDelete(repository.delete(note) > 0, note.getId());
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
    public DataSource.Factory<Integer, Note> getFactoryDataSource() {
        return repository.getFactoryNotes();
    }
}
