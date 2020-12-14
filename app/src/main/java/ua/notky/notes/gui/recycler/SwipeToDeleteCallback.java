package ua.notky.notes.gui.recycler;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.api.AppExecutors;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.tools.utils.AdapterUtil;
import ua.notky.notes.tools.utils.NoteUtil;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private NoteAdapter adapter;
    private NoteService noteService;
    private AppExecutors executors;

    public SwipeToDeleteCallback(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public void setAdapter(NoteAdapter adapter) {
        this.adapter = adapter;
    }

    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    public void setExecutors(AppExecutors executors) {
        this.executors = executors;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        executors.multiple().execute(() -> {
            int position = viewHolder.getAdapterPosition();
            noteService.delete(adapter.getList().get(position));

            List<Note> newList = noteService.getAll();
            NoteUtil.sortWithDate(newList);

            executors.ui().execute(() -> AdapterUtil.update(adapter, newList));
        });
    }
}
