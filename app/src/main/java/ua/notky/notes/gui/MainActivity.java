package ua.notky.notes.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import ua.notky.notes.databinding.ActivityMainBinding;
import ua.notky.notes.gui.model.SharedViewModel;
import ua.notky.notes.gui.presenter.main.MainPresenter;
import ua.notky.notes.gui.presenter.main.MainView;
import ua.notky.notes.tools.Constant;
import ua.notky.notes.tools.utils.ViewUtil;
import ua.notky.notes.tools.dagger.AppDagger;
import ua.notky.notes.tools.enums.LoadDataMode;
import ua.notky.notes.tools.enums.AppMode;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView {

    private SharedPreferences preferences;
    private SharedViewModel viewModel;
    private LoadDataMode loadDataMode;
    private ActivityMainBinding binding;
    @Inject MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbarInclude.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        binding.toolbarInclude.backToolbarBtn.setOnClickListener(this);
        binding.toolbarInclude.saveToolbarBtn.setOnClickListener(this);

        preferences = getPreferences(MODE_PRIVATE);

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.getAppMode().observe(this, this::startMode);
        viewModel.changeAppMode(AppMode.NORMAL);

        AppDagger.getInstance().getComponent().injectMainActivity(this);
        presenter.setView(this);

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
            ViewUtil.createSnackBar(binding.rootView).show();
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
        ViewUtil.createToast(binding.rootView).show();
    }

    private void loadData(Bundle bundle){
        binding.progressBarCircle.setVisibility(View.INVISIBLE);

        if(bundle == null || !bundle.getBoolean(Constant.LOADING_DATA)){
            if(preferences.getBoolean(Constant.FIRST_LAUNCH_APP, true)){
                loadDataMode = LoadDataMode.FIRST;
            } else {
                loadDataMode = LoadDataMode.NORMAL;
            }

            presenter.startLoadData(loadDataMode);
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
        presenter.save();

        viewModel.changeAppMode(AppMode.EDIT);
    }

    protected void onBackToolbar(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.toolbarInclude.backToolbarBtn.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        onBackPressed();
    }
}