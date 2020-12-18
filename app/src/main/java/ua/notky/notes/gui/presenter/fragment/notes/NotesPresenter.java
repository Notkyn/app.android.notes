package ua.notky.notes.gui.presenter.fragment.notes;

import ua.notky.notes.gui.recycler.SwipeToDeleteCallback;
import ua.notky.notes.gui.recycler.NotePagedAdapter;

public interface NotesPresenter {
    void setView(NotesView view);
    void deleteAfterSwipe(int position);
    void addDefaultNote();
    NotePagedAdapter getPageAdapter();
    SwipeToDeleteCallback getSwipe();
}
