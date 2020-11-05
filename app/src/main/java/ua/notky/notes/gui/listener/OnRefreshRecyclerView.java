package ua.notky.notes.gui.listener;

import java.util.List;

import ua.notky.notes.data.model.Note;

public interface OnRefreshRecyclerView {
    public void refresh(List<Note> notes);
}
