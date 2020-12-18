package ua.notky.notes.gui.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.utils.NoteUtil;

public class NoteItemDiffUtilCallback extends DiffUtil.ItemCallback<Note> {
    @Override
    public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
        return NoteUtil.equalsId(oldItem, newItem);
    }

    @Override
    public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
        return NoteUtil.equalsWithoutId(oldItem, newItem);
    }
}
