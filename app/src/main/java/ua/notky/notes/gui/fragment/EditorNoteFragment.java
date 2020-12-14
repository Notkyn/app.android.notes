package ua.notky.notes.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ua.notky.notes.databinding.EditorNoteFragmentBinding;
import ua.notky.notes.gui.listener.EditorTextWatcher;
import ua.notky.notes.gui.presenter.fragment.editornote.EditorNotePresenter;
import ua.notky.notes.gui.presenter.fragment.editornote.EditorNoteView;
import ua.notky.notes.model.Note;
import ua.notky.notes.gui.model.SharedViewModel;
import ua.notky.notes.tools.dagger.AppDagger;

public class EditorNoteFragment extends Fragment implements EditorNoteView {
    private EditorNoteFragmentBinding binding;
    private Note note;
    @Inject EditorNotePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = EditorNoteFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        note = viewModel.getNote();

        binding.titleEditView.setText(note.getTitle());
        binding.descriptionEditView.setText(note.getDescription());

        EditorTextWatcher watcher = new EditorTextWatcher(viewModel, note);
        binding.titleEditView.addTextChangedListener(watcher);
        binding.descriptionEditView.addTextChangedListener(watcher);

        AppDagger.getInstance().getComponent().injectEditorNoteFragment(this);
        presenter.setView(this);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public Note getNoteToSave() {
        note.setTitle(binding.titleEditView.getText().toString());
        note.setDescription(binding.descriptionEditView.getText().toString());
        note.setDate(new Date());

        return note;
    }
}
