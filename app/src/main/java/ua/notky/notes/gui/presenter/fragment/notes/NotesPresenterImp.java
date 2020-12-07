package ua.notky.notes.gui.presenter.fragment.notes;

import java.util.List;

import javax.inject.Inject;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.gui.recycler.OnSelectItemRecyclerView;
import ua.notky.notes.gui.recycler.SwipeToDeleteCallback;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.util.NoteUtil;
import ua.notky.notes.util.ViewUtil;
import ua.notky.notes.util.dagger.AppDagger;

public class NotesPresenterImp implements NotesPresenter, OnSelectItemRecyclerView<Note> {
    private NotesView view;
    private List<Note> notes;
    @Inject NoteService noteService;
    @Inject NoteAdapter adapter;

    public NotesPresenterImp() {
        AppDagger.getInstance().getComponent().injectNotesPresenter(this);
        prepareData();
    }

    private void prepareData(){
        notes = noteService.getAllWithSortDate();

        adapter.setList(notes);
        adapter.setOnSelectItemRecyclerView(this);
    }

    @Override
    public void setView(NotesView view) {
        this.view = view;
    }

    @Override
    public NoteAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void addDefaultNote() {
        Note note = NoteUtil.getDefaultNote();
        noteService.save(note);

        notes.add(0, note);
        adapter.notifyDataSetChanged();
    }

    @Override
    public SwipeToDeleteCallback getSwipe() {
        return ViewUtil.createSwipe(noteService, notes, adapter);
    }

    @Override
    public void selectItem(Note note) {
        view.navigateToEditor(note);
    }
}
