package ua.notky.notes.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

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
import ua.notky.notes.gui.presenter.fragment.notes.NotesPresenter;
import ua.notky.notes.gui.presenter.fragment.notes.NotesView;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.utils.ViewUtil;
import ua.notky.notes.tools.dagger.AppDagger;
import ua.notky.notes.tools.enums.AppMode;

public class NotesFragment extends Fragment implements View.OnClickListener, NotesView {
    private NotesFragmentBinding binding;
    private NavController navController;
    private SharedViewModel viewModel;
    @Inject NotesPresenter presenter;

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
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        AppDagger.getInstance().getComponent().injectNotesFragment(this);
        presenter.setView(this);

        RecyclerView recyclerView = ViewUtil.createRecycler(view);
        recyclerView.setAdapter(presenter.getAdapter());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(presenter.getSwipe());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.fab.getId()){
            onClickFloatingButton();
        }
    }

    private void onClickFloatingButton(){
        presenter.addDefaultNote();
    }

    @Override
    public void navigateToEditor(Note note) {
        viewModel.changeAppMode(AppMode.EDIT);
        viewModel.setNote(note);
        navController.navigate(R.id.editor_note_fragment);
    }
}
