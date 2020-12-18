package ua.notky.notes.gui.recycler;

import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.databinding.NoteItemBinding;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.utils.DateUtil;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final NoteItemBinding binding;

    public NoteViewHolder(NoteItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Note note){
        binding.titleNoteItem.setText(note.getTitle());
        binding.descriptionNoteItem.setText(note.getDescription());
        binding.dateNoteItem.setText(DateUtil.format(note.getDate()));
    }
}
