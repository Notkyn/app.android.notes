package ua.notky.notes.gui.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ua.notky.notes.api.tasks.LoadTask;
import ua.notky.notes.model.Note;
import ua.notky.notes.util.enums.AppMode;

public class SharedViewModel extends ViewModel {
    private Note note;
    private LoadTask loadTask;
    private final MutableLiveData<AppMode> appMode = new MutableLiveData<>();

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public LoadTask getLoadTask() {
        return loadTask;
    }

    public void setLoadTask(LoadTask loadTask) {
        this.loadTask = loadTask;
    }

    public MutableLiveData<AppMode> getAppMode() {
        return appMode;
    }

    public void changeAppMode(AppMode mode){
        appMode.setValue(mode);
    }
}
