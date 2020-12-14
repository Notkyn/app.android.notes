package ua.notky.notes.gui.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.enums.AppMode;

public class SharedViewModel extends ViewModel {
    private Note note;
    private final MutableLiveData<AppMode> appMode = new MutableLiveData<>();

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public MutableLiveData<AppMode> getAppMode() {
        return appMode;
    }

    public void changeAppMode(AppMode mode){
        appMode.setValue(mode);
    }
}
