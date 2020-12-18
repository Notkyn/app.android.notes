package ua.notky.notes.gui.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import ua.notky.notes.databinding.NoteItemBinding;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.dagger.AppDagger;

public class NotePagedAdapter extends PagedListAdapter<Note, NoteViewHolder> {
    private OnSelectItemRecyclerView<Note> onSelectItemRecyclerView;

    public NotePagedAdapter() {
        super(AppDagger.getInstance().getComponent().getNoteItemDiffUtilCallback());
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        NoteItemBinding binding = NoteItemBinding.inflate(inflater, parent, false);
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        final Note note = getItem(position);
        holder.bind(note);
        holder.itemView.setOnClickListener(view -> onSelectItemRecyclerView.selectItem(note));
    }

    public void setOnSelectItemRecyclerView(OnSelectItemRecyclerView<Note> onSelectItemRecyclerView) {
        this.onSelectItemRecyclerView = onSelectItemRecyclerView;
    }
}
