package ua.notky.notes.activity.widgets;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ua.notky.notes.tasks.FirstLoadTask;

public class SaveFragment extends Fragment {
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
