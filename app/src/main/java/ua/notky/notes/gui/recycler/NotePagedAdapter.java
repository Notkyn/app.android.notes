package ua.notky.notes.gui.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import ua.notky.notes.R;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.dagger.AppDagger;
import ua.notky.notes.tools.utils.DateUtil;

public class NotePagedAdapter extends PagedListAdapter<Note, NoteViewHolder> {
    private OnSelectItemRecyclerView<Note> onSelectItemRecyclerView;

    public NotePagedAdapter() {
        super(AppDagger.getInstance().getComponent().getNoteItemDiffUtilCallback());
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent,  false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        final Note note = getItem(position);
        holder.setTitle(Objects.requireNonNull(note).getTitle());
        holder.setDescription(note.getDescription());

        holder.setDate(DateUtil.format(note.getDate()));

        holder.itemView.setOnClickListener(view -> onSelectItemRecyclerView.selectItem(note));
    }

    public void setOnSelectItemRecyclerView(OnSelectItemRecyclerView<Note> onSelectItemRecyclerView) {
        this.onSelectItemRecyclerView = onSelectItemRecyclerView;
    }
}
