package ua.notky.notes.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Date;

import androidx.fragment.app.Fragment;
import ua.notky.notes.R;
import ua.notky.notes.activity.listener.HostActivity;
import ua.notky.notes.activity.listener.OnChangeTextListener;
import ua.notky.notes.activity.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.activity.listener.TextEditListener;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.service.NoteServiceImp;

import static ua.notky.notes.util.NoteUtil.DESCRIPTION;
import static ua.notky.notes.util.NoteUtil.ID;
import static ua.notky.notes.util.NoteUtil.TITLE;

public class EditorNoteFragment extends Fragment implements OnSaveToolbarButtonListener {
    private Note note;
    private NoteService noteService;
    private EditText titleEdit;
    private TextEditListener titleEditListener;
    private EditText descriptionEdit;
    private TextEditListener descriptionEditListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editor_note_fragment, container, false);

        note = getNoteFromArguments(getArguments());
        noteService = new NoteServiceImp(view.getContext());

        titleEdit = view.findViewById(R.id.title_edit_view);
        titleEditListener = new TextEditListener((OnChangeTextListener) getActivity(), note.getTitle());
        titleEdit.addTextChangedListener(titleEditListener);
        descriptionEdit = view.findViewById(R.id.description_edit_view);
        descriptionEditListener = new TextEditListener((OnChangeTextListener) getActivity(), note.getDescription());
        descriptionEdit.addTextChangedListener(descriptionEditListener);

        titleEdit.setText(note.getTitle());
        descriptionEdit.setText(note.getDescription());

        HostActivity activity = (HostActivity) getActivity();
        activity.setSaveToolbarListener(this);

        return view;
    }

    private Note getNoteFromArguments(Bundle bundle){
        if(bundle == null){
            return new Note();
        }
        Note note = new Note();
        note.setId(bundle.getInt(ID));
        note.setTitle(bundle.getString(TITLE));
        note.setDescription(bundle.getString(DESCRIPTION));
        return note;
    }

    @Override
    public void onSave() {
        note.setTitle(titleEdit.getText().toString());
        note.setDescription(descriptionEdit.getText().toString());
        note.setDate(new Date());
        noteService.save(note);
        titleEditListener.setText(note.getTitle());
        descriptionEditListener.setText(note.getDescription());
    }
}
