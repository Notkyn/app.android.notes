package ua.notky.notes.gui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ua.notky.notes.api.tasks.LoadTask;

public class SavedFragment extends Fragment {
    private LoadTask loadTask;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public LoadTask getLoadTask() {
        return loadTask;
    }

    public void setLoadTask(LoadTask loadTask) {
        this.loadTask = loadTask;
    }
}
