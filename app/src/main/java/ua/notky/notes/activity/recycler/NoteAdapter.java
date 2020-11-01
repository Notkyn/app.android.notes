package ua.notky.notes.activity.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;
import ua.notky.notes.model.Note;
import ua.notky.notes.util.DateUtil;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private final Context context;
    private final List<Note> list;
    private final OnSelectItemRecyclerView<Note> onSelectItemRecyclerView;

    public NoteAdapter(Context context, List<Note> list, OnSelectItemRecyclerView<Note> onSelectItemRecyclerView) {
        this.context = context;
        this.list = list;
        this.onSelectItemRecyclerView = onSelectItemRecyclerView;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.note_item, parent,  false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, final int position) {
        final Note note = list.get(position);
        noteViewHolder.setTitle(note.getTitle());
        noteViewHolder.setDescription(note.getDescription());

        noteViewHolder.setDate(DateUtil.format(note.getDate()));

        noteViewHolder.itemView.setOnClickListener(view -> onSelectItemRecyclerView.selectItem(note));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
