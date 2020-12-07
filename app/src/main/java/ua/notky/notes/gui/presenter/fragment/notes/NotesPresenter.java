package ua.notky.notes.gui.presenter.fragment.notes;

import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.gui.recycler.SwipeToDeleteCallback;

public interface NotesPresenter {
    void setView(NotesView view);
    NoteAdapter getAdapter();
    void addDefaultNote();
    SwipeToDeleteCallback getSwipe();
}
