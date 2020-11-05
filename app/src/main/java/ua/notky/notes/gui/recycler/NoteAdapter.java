package ua.notky.notes.gui.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;
import ua.notky.notes.data.model.Note;
import ua.notky.notes.util.DateUtil;
import ua.notky.notes.util.NoteUtil;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private Context context;
    private List<Note> list;
    private OnSelectItemRecyclerView<Note> onSelectItemRecyclerView;

    public NoteAdapter() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setList(List<Note> list) {
        this.list = list;
    }

    public void setOnSelectItemRecyclerView(OnSelectItemRecyclerView<Note> onSelectItemRecyclerView) {
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

    public void dataChanged( List<Note> notes){
        list.clear();
        list.addAll(notes);
        NoteUtil.sortWithDate(list);
        notifyDataSetChanged();
    }
}