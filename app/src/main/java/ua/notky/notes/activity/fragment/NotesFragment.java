package ua.notky.notes.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;
import ua.notky.notes.listener.OnSelectItemToEditListener;
import ua.notky.notes.recycler.NoteAdapter;
import ua.notky.notes.recycler.OnSelectItemRecyclerView;
import ua.notky.notes.recycler.SwipeToDeleteCallback;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.service.NoteServiceImp;
import ua.notky.notes.util.NoteUtil;
import ua.notky.notes.util.RecyclerUtil;

import static ua.notky.notes.util.NoteUtil.DESCRIPTION;
import static ua.notky.notes.util.NoteUtil.ID;
import static ua.notky.notes.util.NoteUtil.TITLE;

public class NotesFragment extends Fragment implements OnSelectItemRecyclerView<Note>, View.OnClickListener {
    private NavController navController;
    private OnSelectItemToEditListener toEditListener;
    private NoteService noteService;
    private RecyclerView recyclerView;
    private List<Note> notes;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes_fragment, container, false);

        view.findViewById(R.id.fab).setOnClickListener(this);

        navController = NavHostFragment.findNavController(this);
        toEditListener = (OnSelectItemToEditListener) getActivity();

        noteService = new NoteServiceImp(view.getContext());
        notes = noteService.getAll();
        NoteUtil.sortWithDate(notes);


        recyclerView = RecyclerUtil.createRecycler(view);
        NoteAdapter adapter = new NoteAdapter(this.getContext(), notes, this);
        recyclerView.setAdapter(adapter);

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, adapter, notes, noteService);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void selectItem(Note note) {
        Bundle bundle = new Bundle();
        bundle.putInt(ID, note.getId());
        bundle.putString(TITLE, note.getTitle());
        bundle.putString(DESCRIPTION, note.getDescription());
        toEditListener.onSelectItemToEdit();
        navController.navigate(R.id.editor_note_fragment, bundle);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab){
            onClickFloatingButton();
        }
    }

    private void onClickFloatingButton(){
        Note note = new Note();
        note.setTitle("Новая заметка");
        note.setDescription("");
        note.setDate(new Date());
        noteService.save(note);

        notes.add(0, note);
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
