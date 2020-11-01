package ua.notky.notes.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;
import ua.notky.notes.activity.recycler.NoteAdapter;
import ua.notky.notes.activity.recycler.OnSelectItemRecyclerView;
import ua.notky.notes.activity.recycler.SwipeToDeleteCallback;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.service.NoteServiceImp;
import ua.notky.notes.util.NoteUtil;
import ua.notky.notes.util.RecyclerUtil;

public class NotesFragment extends Fragment implements OnSelectItemRecyclerView<Note> {
    private NavController navController;
    private NoteService noteService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes_fragment, container, false);
        navController = NavHostFragment.findNavController(this);
        noteService = new NoteServiceImp(view.getContext());
        List<Note> notes = noteService.getAll();
        NoteUtil.sortWithDate(notes);

        RecyclerView recyclerView = RecyclerUtil.createRecycler(view);
        NoteAdapter adapter = new NoteAdapter(view.getContext(), notes, this);
        recyclerView.setAdapter(adapter);

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, adapter, notes, noteService);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void selectItem(Note obj) {
        navController.navigate(R.id.editor_note_fragment);
    }
}
