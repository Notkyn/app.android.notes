package ua.notky.notes.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;
import ua.notky.notes.databinding.NotesFragmentBinding;
import ua.notky.notes.gui.model.SharedViewModel;
import ua.notky.notes.gui.recycler.OnSelectItemRecyclerView;
import ua.notky.notes.gui.recycler.SwipeToDeleteCallback;
import ua.notky.notes.data.model.Note;
import ua.notky.notes.data.service.NoteService;
import ua.notky.notes.data.service.NoteServiceImp;
import ua.notky.notes.util.NoteUtil;
import ua.notky.notes.util.RecyclerUtil;
import ua.notky.notes.util.enums.AppMode;

public class NotesFragment extends Fragment implements OnSelectItemRecyclerView<Note>, View.OnClickListener {
    private NotesFragmentBinding binding;
    private NavController navController;
    private NoteService noteService;
    private List<Note> notes;
    private SharedViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = NotesFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.fab.setOnClickListener(this);

        navController = NavHostFragment.findNavController(this);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

        noteService = new NoteServiceImp(view.getContext());
        notes = noteService.getAllWithSortDate();

        viewModel.getAdapter().setContext(this.getContext());
        viewModel.getAdapter().setList(notes);
        viewModel.getAdapter().setOnSelectItemRecyclerView(this);
        RecyclerView recyclerView = RecyclerUtil.createRecycler(view);
        recyclerView.setAdapter(viewModel.getAdapter());

        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, viewModel.getAdapter(), notes, noteService);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void selectItem(Note note) {
        viewModel.changeAppMode(AppMode.EDIT);
        viewModel.setNote(note);
        navController.navigate(R.id.editor_note_fragment);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.fab.getId()){
            onClickFloatingButton();
        }
    }

    private void onClickFloatingButton(){
        Note note = NoteUtil.getDefaultNote();
        noteService.save(note);

        notes.add(0, note);
        viewModel.getAdapter().notifyDataSetChanged();
    }
}
