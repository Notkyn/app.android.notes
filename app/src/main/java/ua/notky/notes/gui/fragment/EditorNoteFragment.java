package ua.notky.notes.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ua.notky.notes.databinding.EditorNoteFragmentBinding;
import ua.notky.notes.gui.listener.EditorTextWatcher;
import ua.notky.notes.gui.listener.HostActivity;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.data.model.Note;
import ua.notky.notes.data.service.NoteService;
import ua.notky.notes.data.service.NoteServiceImp;
import ua.notky.notes.gui.model.SharedViewModel;

public class EditorNoteFragment extends Fragment implements OnSaveToolbarButtonListener {
    private EditorNoteFragmentBinding binding;
    private Note note;
    private NoteService noteService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HostActivity activity = (HostActivity) getActivity();
        if(activity != null){
            activity.setSaveToolbarListener(this);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = EditorNoteFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        note = viewModel.getNote();

        noteService = new NoteServiceImp(view.getContext());

        binding.titleEditView.setText(note.getTitle());
        binding.descriptionEditView.setText(note.getDescription());

        EditorTextWatcher watcher = new EditorTextWatcher(viewModel, note);
        binding.titleEditView.addTextChangedListener(watcher);
        binding.descriptionEditView.addTextChangedListener(watcher);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onSave() {
        note.setTitle(binding.titleEditView.getText().toString());
        note.setDescription(binding.descriptionEditView.getText().toString());
        note.setDate(new Date());

        noteService.save(note);
    }
}
