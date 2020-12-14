package ua.notky.notes.gui.presenter.fragment.notes;

import java.util.List;

import javax.inject.Inject;

import ua.notky.notes.api.AppExecutors;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.gui.recycler.OnSelectItemRecyclerView;
import ua.notky.notes.gui.recycler.SwipeToDeleteCallback;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.tools.utils.AdapterUtil;
import ua.notky.notes.tools.utils.NoteUtil;
import ua.notky.notes.tools.utils.ViewUtil;
import ua.notky.notes.tools.dagger.AppDagger;

public class NotesPresenterImp implements NotesPresenter, OnSelectItemRecyclerView<Note> {
    private NotesView view;
    private List<Note> notes;
    @Inject NoteService noteService;
    @Inject NoteAdapter adapter;
    @Inject AppExecutors executors;

    public NotesPresenterImp() {
        AppDagger.getInstance().getComponent().injectNotesPresenter(this);
        prepareData();
    }

    private void prepareData(){
        executors.multiple().execute(() -> {
            notes = noteService.getAllWithSortDate();

            adapter.setList(notes);
        });
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
        executors.multiple().execute(() -> {
            Note note = NoteUtil.getDefaultNote();
            noteService.save(note);

            List<Note> newList = noteService.getAll();
            NoteUtil.sortWithDate(newList);

            executors.ui().execute(() -> AdapterUtil.update(adapter, newList));
        });
    }

    @Override
    public SwipeToDeleteCallback getSwipe() {
        return ViewUtil.createSwipe(noteService, adapter, executors);
    }

    @Override
    public void selectItem(Note note) {
        view.navigateToEditor(note);
    }
}
