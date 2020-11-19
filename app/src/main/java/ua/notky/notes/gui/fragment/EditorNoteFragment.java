package ua.notky.notes.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import ua.notky.notes.R;
import ua.notky.notes.gui.listener.HostActivity;
import ua.notky.notes.gui.listener.OnChangeTextListener;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.data.model.Note;
import ua.notky.notes.data.service.NoteService;
import ua.notky.notes.data.service.NoteServiceImp;
import ua.notky.notes.gui.model.SharedViewModel;
import ua.notky.notes.util.enums.TextState;

public class EditorNoteFragment extends Fragment implements OnSaveToolbarButtonListener {
    @BindView(R.id.title_edit_view) protected EditText titleEdit;
    @BindView(R.id.description_edit_view) protected EditText descriptionEdit;

    private Unbinder unbinder;
    private SharedViewModel viewModel;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editor_note_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        note = viewModel.getNote();

        noteService = new NoteServiceImp(view.getContext());

        titleEdit.setText(note.getTitle());
        descriptionEdit.setText(note.getDescription());
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onSave() {
        note.setTitle(titleEdit.getText().toString());
        note.setDescription(descriptionEdit.getText().toString());
        note.setDate(new Date());

        noteService.save(note);
    }

    @OnTextChanged(R.id.title_edit_view)
    public void onTitleTextChanged(CharSequence charSequence, int start, int before, int count) {
        setTextStateAfterChangeText(note.getTitle(), charSequence);
    }

    @OnTextChanged(R.id.description_edit_view)
    public void onDescriptionTextChanged(CharSequence charSequence, int start, int before, int count) {
        setTextStateAfterChangeText(note.getDescription(), charSequence);
    }

    private void setTextStateAfterChangeText(String text, CharSequence changedText){
        if(text.contentEquals(changedText)){
            viewModel.changeText(TextState.CURRENT);
        } else {
            viewModel.changeText(TextState.CHANGED);
        }
    }
}
