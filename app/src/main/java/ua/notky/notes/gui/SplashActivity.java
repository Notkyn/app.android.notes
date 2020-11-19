package ua.notky.notes.gui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import ua.notky.notes.R;
import ua.notky.notes.gui.model.SharedViewModel;
import ua.notky.notes.util.PrintHelper;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        findViewById(R.id.splash_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.splash_btn) {
            PrintHelper.print(viewModel);
            PrintHelper.print(viewModel.getCounter());
            PrintHelper.print(viewModel.getCounter().getValue());



            int index = viewModel.getCounter().getValue() == null ? 0 : viewModel.getCounter().getValue();
            viewModel.changeCounter(++index);
        }
    }
}
