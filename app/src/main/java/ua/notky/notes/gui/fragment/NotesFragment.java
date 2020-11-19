package ua.notky.notes.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;
import ua.notky.notes.gui.listener.HostActivity;
import ua.notky.notes.gui.listener.OnSelectItemToEditListener;
import ua.notky.notes.gui.model.SharedViewModel;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.gui.recycler.OnSelectItemRecyclerView;
import ua.notky.notes.gui.recycler.SwipeToDeleteCallback;
import ua.notky.notes.data.model.Note;
import ua.notky.notes.data.service.NoteService;
import ua.notky.notes.data.service.NoteServiceImp;
import ua.notky.notes.util.NoteUtil;
import ua.notky.notes.util.RecyclerUtil;

public class NotesFragment extends Fragment implements OnSelectItemRecyclerView<Note>, View.OnClickListener {
    private NavController navController;
    private OnSelectItemToEditListener toEditListener;
    private NoteService noteService;
    private NoteAdapter adapter;
    private List<Note> notes;
    private SharedViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notes_fragment, container, false);

        view.findViewById(R.id.fab).setOnClickListener(this);

        navController = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        toEditListener = (OnSelectItemToEditListener) getActivity();

        noteService = new NoteServiceImp(view.getContext());
        notes = noteService.getAll();
        NoteUtil.sortWithDate(notes);

        HostActivity activity = (HostActivity) getActivity();
        if(activity != null){
            adapter = activity.getAdapter();
            adapter.setContext(this.getContext());
            adapter.setList(notes);
            adapter.setOnSelectItemRecyclerView(this);
        }
        RecyclerView recyclerView = RecyclerUtil.createRecycler(view);
        recyclerView.setAdapter(adapter);

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, adapter, notes, noteService);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void selectItem(Note note) {
        toEditListener.onSelectItemToEdit();
        viewModel.setNote(note);
        navController.navigate(R.id.editor_note_fragment);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.fab){
            onClickFloatingButton();
        }
    }

    private void onClickFloatingButton(){
        Note note = NoteUtil.getDefaultNote();
        noteService.save(note);

        notes.add(0, note);
        adapter.notifyDataSetChanged();
    }
}
