package ua.notky.notes.gui.recycler;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.utils.NoteUtil;

public class NoteDiffUtilCallback extends DiffUtil.Callback {
    private List<Note> oldList;
    private List<Note> newList;

    public void setData(List<Note> oldList, List<Note> newList){
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return NoteUtil.equalsId(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return NoteUtil.equalsWithoutId(oldList.get(oldItemPosition), newList.get(newItemPosition));
    }
}
