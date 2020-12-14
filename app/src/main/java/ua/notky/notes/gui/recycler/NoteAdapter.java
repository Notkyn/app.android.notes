package ua.notky.notes.gui.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.utils.DateUtil;
import ua.notky.notes.tools.dagger.AppDagger;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private List<Note> list;
    private OnSelectItemRecyclerView<Note> onSelectItemRecyclerView;
    @Inject Context context;

    public NoteAdapter() {
        AppDagger.getInstance().getComponent().injectNoteAdapter(this);
    }

    public void setList(List<Note> list) {
        this.list = list;
    }

    public List<Note> getList() {
        return list;
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
}
