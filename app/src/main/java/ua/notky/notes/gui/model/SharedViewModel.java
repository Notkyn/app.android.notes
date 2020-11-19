package ua.notky.notes.gui.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ua.notky.notes.data.model.Note;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.util.enums.TextState;

public class SharedViewModel extends ViewModel {
    private Note note;
    private final MutableLiveData<TextState> textState = new MutableLiveData<>();


    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public MutableLiveData<TextState> getTextState() {
        return textState;
    }

    public void changeText(TextState state){
        textState.setValue(state);
    }
}
