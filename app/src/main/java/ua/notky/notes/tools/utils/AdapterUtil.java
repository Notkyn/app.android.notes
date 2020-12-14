package ua.notky.notes.tools.utils;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.gui.recycler.NoteDiffUtilCallback;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.dagger.AppDagger;

public class AdapterUtil {

    public static void update(NoteAdapter adapter, List<Note> list){
        NoteDiffUtilCallback callback = AppDagger.getInstance().getComponent().getNoteDiffUtilCallback();
        callback.setData(adapter.getList(), list);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        adapter.setList(list);
        result.dispatchUpdatesTo(adapter);
    }
}
