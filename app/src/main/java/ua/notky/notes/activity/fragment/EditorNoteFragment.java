package ua.notky.notes.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import ua.notky.notes.R;

public class EditorNoteFragment extends Fragment implements View.OnClickListener{
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editor_note_fragment, container, false);
        navController = NavHostFragment.findNavController(this);
        view.findViewById(R.id.go_list).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.go_list){
            goToList();
        }
    }

    private void goToList(){
        navController.navigate(R.id.notes_fragment);
    }
}
