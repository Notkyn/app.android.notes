package ua.notky.notes.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import ua.notky.notes.databinding.ActivityMainBinding;
import ua.notky.notes.gui.model.SharedViewModel;
import ua.notky.notes.gui.presenter.main.MainPresenter;
import ua.notky.notes.gui.presenter.main.MainView;
import ua.notky.notes.tools.Constant;
import ua.notky.notes.tools.dagger.AppDagger;
import ua.notky.notes.tools.enums.LaunchMode;
import ua.notky.notes.tools.enums.AppMode;
import ua.notky.notes.tools.utils.ViewUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MainView {

    private SharedPreferences preferences;
    private SharedViewModel viewModel;
    private LaunchMode launchMode;
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

        setLaunchMode();
        presenter.startLoadData();
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
                break;
        }
    }

    private void setLaunchMode(){
        if(preferences.getBoolean(Constant.FIRST_LAUNCH_APP, true)){
            launchMode = LaunchMode.FIRST;
            binding.viewProgressbarFirstLaunch.setVisibility(View.VISIBLE);
            binding.progressBarFirstLaunch.setVisibility(View.VISIBLE);
            binding.infoFirstLaunch.setVisibility(View.INVISIBLE);
        } else {
            launchMode = LaunchMode.NORMAL;
            binding.viewProgressbarFirstLaunch.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showProgressBar() {
        switch (launchMode) {
            case FIRST:
                binding.viewProgressbarFirstLaunch.setVisibility(View.VISIBLE);
                binding.progressBarFirstLaunch.setVisibility(View.VISIBLE);
                binding.infoFirstLaunch.setVisibility(View.INVISIBLE);
                break;
            case NORMAL:
                binding.progressBarNormalLaunch.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void hideProgressBar() {
        switch (launchMode) {
            case FIRST:
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Constant.FIRST_LAUNCH_APP, false);
                editor.apply();

                binding.viewProgressbarFirstLaunch.setVisibility(View.INVISIBLE);
                launchMode = LaunchMode.NORMAL;
                break;
            case NORMAL:
                binding.progressBarNormalLaunch.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void showEmptyResult() {
        if(launchMode == LaunchMode.NORMAL){
            ViewUtil.createToast(binding.rootView).show();
        }
    }

    @Override
    public void setProgressValue(int value) {
        if(launchMode == LaunchMode.NORMAL){
            binding.progressBarNormalLaunch.setProgress(value);
        }
    }

    @Override
    public void showNotConnection() {
        switch (launchMode){
            case FIRST:
                binding.progressBarFirstLaunch.setVisibility(View.INVISIBLE);
                binding.infoFirstLaunch.setVisibility(View.VISIBLE);
                break;
            case NORMAL:
                ViewUtil.createSnackBar(binding.rootView).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.changeAppMode(AppMode.NORMAL);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(Constant.VISIBLE_PROGRESS_BAR_FIRST_LAUNCH,
                binding.progressBarFirstLaunch.getVisibility());
        outState.putInt(Constant.VISIBLE_INFO_FIRST_LAUNCH,
                binding.infoFirstLaunch.getVisibility());
        outState.putInt(Constant.VISIBLE_PROGRESS_BAR_NORMAL_LAUNCH,
                binding.progressBarNormalLaunch.getVisibility());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        binding.progressBarFirstLaunch.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_PROGRESS_BAR_FIRST_LAUNCH));
        binding.infoFirstLaunch.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_INFO_FIRST_LAUNCH));
        binding.progressBarNormalLaunch.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_PROGRESS_BAR_NORMAL_LAUNCH));

        super.onRestoreInstanceState(savedInstanceState);
    }
}