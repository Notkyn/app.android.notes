package ua.notky.notes.gui.listener;

import ua.notky.notes.gui.recycler.NoteAdapter;

public interface HostActivity {
    void setSaveToolbarListener(OnSaveToolbarButtonListener onSaveToolbarButtonListener);
    NoteAdapter getAdapter();
}
