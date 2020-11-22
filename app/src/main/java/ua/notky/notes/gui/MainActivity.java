package ua.notky.notes.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import ua.notky.notes.R;
import ua.notky.notes.databinding.ActivityMainBinding;
import ua.notky.notes.gui.listener.LoadingData;
import ua.notky.notes.gui.listener.HostActivity;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.api.tasks.LoadTask;
import ua.notky.notes.gui.model.SharedViewModel;
import ua.notky.notes.util.Constant;
import ua.notky.notes.util.enums.LoadDataMode;
import ua.notky.notes.util.enums.AppMode;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements HostActivity, LoadingData, View.OnClickListener {

    private SharedPreferences preferences;
    private SharedViewModel viewModel;
    private LoadDataMode loadDataMode;
    private OnSaveToolbarButtonListener onSaveToolbarButtonListener;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarInclude.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        preferences = getPreferences(MODE_PRIVATE);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.getAppMode().observe(this, this::startMode);
        viewModel.changeAppMode(AppMode.NORMAL);

        loadData(savedInstanceState);
    }

    private void startMode(AppMode startAppMode){
        switch (startAppMode) {
            case NORMAL:
                binding.toolbarInclude.searchToolbarView.setVisibility(View.VISIBLE);
                binding.toolbarInclude.backToolbarBtn.setVisibility(View.INVISIBLE);
                binding.toolbarInclude.saveToolbarBtn.setVisibility(View.INVISIBLE);
                break;
            case EDIT:
                binding.toolbarInclude.searchToolbarView.setVisibility(View.INVISIBLE);
                binding.toolbarInclude.backToolbarBtn.setVisibility(View.VISIBLE);
                binding.toolbarInclude.saveToolbarBtn.setVisibility(View.INVISIBLE);
                break;
            case SAVE:
                binding.toolbarInclude.searchToolbarView.setVisibility(View.INVISIBLE);
                binding.toolbarInclude.backToolbarBtn.setVisibility(View.VISIBLE);
                binding.toolbarInclude.saveToolbarBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setSaveToolbarListener(OnSaveToolbarButtonListener onSaveToolbarButtonListener) {
        this.onSaveToolbarButtonListener = onSaveToolbarButtonListener;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.changeAppMode(AppMode.NORMAL);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(Constant.LOADING_DATA, true);
        outState.putInt(Constant.VISIBLE_CIRCLE_PROGRESS_BAR,
                binding.progressBarCircle.getVisibility());
        outState.putInt(Constant.VISIBLE_HORIZONTAL_PROGRESS_BAR,
                binding.horizontalProgressBar.getVisibility());
        outState.putInt(Constant.VISIBLE_PROGRESS_BAR,
                binding.progressbarView.getVisibility());
        outState.putInt(Constant.VISIBLE_CONNECTION,
                binding.notConnection.getVisibility());
        outState.putString(Constant.LOAD_MODE, loadDataMode.toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        binding.progressBarCircle.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_CIRCLE_PROGRESS_BAR));
        binding.horizontalProgressBar.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_HORIZONTAL_PROGRESS_BAR));
        binding.progressbarView.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_PROGRESS_BAR));
        binding.notConnection.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_CONNECTION));
        loadDataMode = LoadDataMode.valueOf(savedInstanceState.getString(Constant.LOAD_MODE));

        LoadTask loadTask = viewModel.getLoadTask();
        loadTask.setLauncher(this);

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        binding.progressBarCircle.setVisibility(View.INVISIBLE);

        super.onDestroy();
    }

    @Override
    public void setStateOnlineFirstLoad(boolean isOnline) {
        if(isOnline){
            binding.progressbarView.setVisibility(View.VISIBLE);
            binding.notConnection.setVisibility(View.INVISIBLE);
        } else {
            binding.progressBarCircle.setVisibility(View.INVISIBLE);
            binding.progressbarView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setStateOnlineNormalLoad(boolean showSnackBar, int progress) {
        if(showSnackBar){
            Snackbar snackbar = Snackbar.make(binding.rootView, getResources().getText(R.string.not_connection), Snackbar.LENGTH_LONG);
            snackbar.setTextColor(getResources().getColor(R.color.black));
            snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryVariant));
            snackbar.show();
        }

        binding.horizontalProgressBar.setProgress(progress);
    }

    @Override
    public void showProgressBar() {
        switch (loadDataMode) {
            case FIRST:
                binding.progressBarCircle.setVisibility(View.VISIBLE);
                break;
            case NORMAL:
                binding.horizontalProgressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void hideProgressBar() {
        switch (loadDataMode) {
            case FIRST:
                Editor editor = preferences.edit();
                editor.putBoolean(Constant.FIRST_LAUNCH_APP, false);
                editor.apply();

                binding.progressBarCircle.setVisibility(View.INVISIBLE);
                break;
            case NORMAL:
                binding.horizontalProgressBar.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void showEmptyResult() {
        Toast.makeText(this, getResources().getString(R.string.not_have_data), Toast.LENGTH_SHORT)
                .show();
    }

    private void loadData(Bundle bundle){
        binding.progressBarCircle.setVisibility(View.INVISIBLE);

        if(bundle == null || !bundle.getBoolean(Constant.LOADING_DATA)){
            if(preferences.getBoolean(Constant.FIRST_LAUNCH_APP, true)){
                loadDataMode = LoadDataMode.FIRST;
            } else {
                loadDataMode = LoadDataMode.NORMAL;
            }
            LoadTask loadTask = new LoadTask(this, this);
            loadTask.setAdapter(viewModel.getAdapter());
            loadTask.setMode(loadDataMode);
            loadTask.execute();

            viewModel.setLoadTask(loadTask);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.toolbarInclude.saveToolbarBtn.getId()){
            onSaveToolbar();
        }
        if(view.getId() == binding.toolbarInclude.backToolbarBtn.getId()){
            onBackToolbar();
        }
    }

    private void onSaveToolbar(){
        onSaveToolbarButtonListener.onSave();

        viewModel.changeAppMode(AppMode.EDIT);
    }

    protected void onBackToolbar(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.toolbarInclude.backToolbarBtn.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        onBackPressed();
    }
}