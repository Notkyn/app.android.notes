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
    private final MutableLiveData<Note> selected = new MutableLiveData<>();
    private final MutableLiveData<List<Note>> adapterList = new MutableLiveData<>();
    private final MutableLiveData<NoteAdapter> noteAdapter = new MutableLiveData<>();
    private final MutableLiveData<Integer> counter = new MutableLiveData<>();

    public MutableLiveData<TextState> getTextState() {
        return textState;
    }

    public void changeText(TextState state){
        textState.setValue(state);
    }

    public MutableLiveData<Integer> getCounter() {
        return counter;
    }

    public void changeCounter(int index){
        counter.setValue(index);
    }

    public MutableLiveData<NoteAdapter> getNoteAdapter() {
        return noteAdapter;
    }

    public void setNoteAdapter(NoteAdapter adapter){
        noteAdapter.setValue(adapter);
    }

    public MutableLiveData<List<Note>> getAdapterList() {
        return adapterList;
    }

    public void setAdapterList(List<Note> list) {
        this.adapterList.setValue(list);
    }

    public void select(Note note){
        selected.setValue(note);
    }

    public LiveData<Note> getSelected(){
        return selected;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
