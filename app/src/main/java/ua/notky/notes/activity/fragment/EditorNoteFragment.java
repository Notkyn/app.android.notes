package ua.notky.notes.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import ua.notky.notes.R;
import ua.notky.notes.model.Note;

import static ua.notky.notes.util.NoteUtil.DESCRIPTION;
import static ua.notky.notes.util.NoteUtil.ID;
import static ua.notky.notes.util.NoteUtil.TITLE;

public class EditorNoteFragment extends Fragment{
    private NavController navController;
    private Note note;
    private EditText titleEdit;
    private EditText descriptionEdit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editor_note_fragment, container, false);
        navController = NavHostFragment.findNavController(this);

        note = getNoteFromArguments(getArguments());

        titleEdit = view.findViewById(R.id.title_edit_view);
        descriptionEdit = view.findViewById(R.id.description_edit_view);

        titleEdit.setText(note.getTitle());
        descriptionEdit.setText(note.getDescription());

        return view;
    }

    private void goToList(){
        navController.navigate(R.id.notes_fragment);
    }

    private Note getNoteFromArguments(Bundle bundle){
        Note note = new Note();
        note.setId(bundle.getInt(ID));
        note.setTitle(bundle.getString(TITLE));
        note.setDescription(bundle.getString(DESCRIPTION));
        return note;
    }
}
