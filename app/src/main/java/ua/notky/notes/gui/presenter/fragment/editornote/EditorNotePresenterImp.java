package ua.notky.notes.gui.presenter.fragment.editornote;

import javax.inject.Inject;

import ua.notky.notes.api.AppExecutors;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.util.dagger.AppDagger;

public class EditorNotePresenterImp implements EditorNotePresenter, OnSaveToolbarButtonListener {
    @Inject NoteService service;
    @Inject AppExecutors executors;
    private EditorNoteView view;

    public EditorNotePresenterImp() {
        AppDagger.getInstance().getComponent().injectEditorNotePresenter(this);
    }

    @Override
    public void setView(EditorNoteView view) {
        this.view = view;
    }

    @Override
    public void onSave() {
        executors.multiple().execute(() -> {
            service.save(view.getNoteToSave());
        });
    }
}
