package ua.notky.notes.gui.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ua.notky.notes.api.tasks.FirstLoadTask;

public class SavedFragment extends Fragment {
    private FirstLoadTask firstLoadTask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public FirstLoadTask getFirstLoadTask() {
        return firstLoadTask;
    }

    public void setFirstLoadTask(FirstLoadTask firstLoadTask) {
        this.firstLoadTask = firstLoadTask;
    }
}
