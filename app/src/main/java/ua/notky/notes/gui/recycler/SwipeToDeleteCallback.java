package ua.notky.notes.gui.recycler;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.util.dagger.AppDagger;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private List<Note> notes;
    private NoteAdapter adapter;
    private NoteService noteService;

    public SwipeToDeleteCallback(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public void setAdapter(NoteAdapter adapter) {
        this.adapter = adapter;
    }

    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        noteService.delete(notes.get(position).getId());
        notes.remove(position);
        adapter.notifyDataSetChanged();
    }
}
