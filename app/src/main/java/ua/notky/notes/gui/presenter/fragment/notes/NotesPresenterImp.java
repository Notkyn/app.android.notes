package ua.notky.notes.gui.presenter.fragment.notes;

import javax.inject.Inject;

import ua.notky.notes.api.AppExecutors;
import ua.notky.notes.gui.recycler.SwipeToDeleteCallback;
import ua.notky.notes.gui.recycler.NotePagedAdapter;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.tools.utils.NoteUtil;
import ua.notky.notes.tools.utils.ViewUtil;
import ua.notky.notes.tools.dagger.AppDagger;

public class NotesPresenterImp implements NotesPresenter {
    private NotesView view;
    @Inject NoteService noteService;
    @Inject NotePagedAdapter pagedAdapter;
    @Inject AppExecutors executors;

    public NotesPresenterImp() {
        AppDagger.getInstance().getComponent().injectNotesPresenter(this);
    }

    @Override
    public void setView(NotesView view) {
        this.view = view;
    }

    @Override
    public NotePagedAdapter getPageAdapter() {
        return pagedAdapter;
    }

    @Override
    public void deleteAfterSwipe(int position) {
        executors.multiple().execute(() -> {
            noteService.delete(pagedAdapter.getCurrentList().get(position));
        });
    }

    @Override
    public void addDefaultNote() {
        executors.multiple().execute(() -> noteService.save(NoteUtil.getDefaultNote()));
    }

    @Override
    public SwipeToDeleteCallback getSwipe() {
        return ViewUtil.createSwipe(this);
    }
}
