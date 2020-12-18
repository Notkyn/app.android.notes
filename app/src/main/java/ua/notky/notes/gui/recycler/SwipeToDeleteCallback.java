package ua.notky.notes.gui.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.gui.presenter.fragment.notes.NotesPresenter;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final NotesPresenter presenter;

    public SwipeToDeleteCallback(int dragDirs, int swipeDirs, NotesPresenter presenter) {
        super(dragDirs, swipeDirs);
        this.presenter = presenter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        presenter.deleteAfterSwipe(viewHolder.getAdapterPosition());
    }
}
