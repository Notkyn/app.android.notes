package ua.notky.notes.gui.listener;

import android.text.Editable;
import android.text.TextWatcher;

import ua.notky.notes.model.Note;
import ua.notky.notes.gui.model.SharedViewModel;
import ua.notky.notes.tools.enums.AppMode;

public class EditorTextWatcher implements TextWatcher {
    private final SharedViewModel viewModel;
    private final Note note;

    public EditorTextWatcher(SharedViewModel viewModel, Note note) {
        this.viewModel = viewModel;
        this.note = note;
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(note.getTitle().contentEquals(charSequence)
                || note.getDescription().contentEquals(charSequence)){
            viewModel.changeAppMode(AppMode.EDIT);
        } else {
            viewModel.changeAppMode(AppMode.SAVE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
