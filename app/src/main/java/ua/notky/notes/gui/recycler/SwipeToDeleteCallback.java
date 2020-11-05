package ua.notky.notes.gui.recycler;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.data.model.Note;
import ua.notky.notes.data.service.NoteService;
import ua.notky.notes.util.PrintHelper;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final NoteAdapter adapter;
    private final List<Note> notes;
    private final NoteService noteService;

    public SwipeToDeleteCallback(int dragDirs, int swipeDirs, NoteAdapter adapter, List<Note> notes, NoteService noteService) {
        super(dragDirs, swipeDirs);
        this.adapter = adapter;
        this.notes = notes;
        this.noteService = noteService;
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
