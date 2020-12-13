package ua.notky.notes.util;

import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;
import ua.notky.notes.api.AppExecutors;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.gui.recycler.SwipeToDeleteCallback;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;

public class ViewUtil {
    private ViewUtil() {
    }

    public static RecyclerView createRecycler(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return recyclerView;
    }

    public static SwipeToDeleteCallback createSwipe(NoteService service,
                                                    List<Note> notes,
                                                    NoteAdapter adapter,
                                                    AppExecutors executors){
        SwipeToDeleteCallback swipe = new SwipeToDeleteCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        swipe.setNoteService(service);
        swipe.setNotes(notes);
        swipe.setAdapter(adapter);
        swipe.setExecutors(executors);
        return swipe;
    }

    public static Snackbar createSnackbar(View view){
        Snackbar snackbar = Snackbar.make(view, view.getResources().getText(R.string.not_connection), Snackbar.LENGTH_LONG);
        snackbar.setTextColor(view.getResources().getColor(R.color.black));
        snackbar.setBackgroundTint(view.getResources().getColor(R.color.colorPrimaryVariant));
        return snackbar;
    }

    public static Toast createToast(View view){
        return Toast.makeText(view.getContext(), view.getResources().getString(R.string.not_have_data), Toast.LENGTH_SHORT);
    }
}
