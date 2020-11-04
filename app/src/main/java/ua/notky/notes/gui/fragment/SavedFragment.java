package ua.notky.notes.gui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ua.notky.notes.api.tasks.FirstLoadTask;
import ua.notky.notes.gui.widgets.ProgressBarDialog;

public class SavedFragment extends Fragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

}
