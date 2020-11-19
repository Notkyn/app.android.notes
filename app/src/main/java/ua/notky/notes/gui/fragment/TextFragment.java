package ua.notky.notes.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ua.notky.notes.R;
import ua.notky.notes.gui.model.SharedViewModel;

public class TextFragment extends Fragment {
    private TextView mText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.text_fragment, container, false);
        mText = view.findViewById(R.id.splash_text);


        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        viewModel.getCounter().observe(requireActivity(), integer -> {
            mText.setText(String.valueOf(integer));
        });

        return view;
    }
}
