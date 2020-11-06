package ua.notky.notes.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import ua.notky.notes.R;
import ua.notky.notes.gui.fragment.SavedFragment;
import ua.notky.notes.gui.listener.LoadingData;
import ua.notky.notes.gui.listener.HostActivity;
import ua.notky.notes.gui.listener.OnChangeTextListener;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.gui.listener.OnSelectItemToEditListener;
import ua.notky.notes.api.tasks.LoadTask;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.util.Constant;
import ua.notky.notes.util.enums.LoadDataMode;
import ua.notky.notes.util.enums.AppMode;
import ua.notky.notes.util.enums.TextState;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnSelectItemToEditListener,
        OnChangeTextListener, HostActivity, LoadingData {
    private SharedPreferences preferences;
    private RelativeLayout circleProgressBar;
    private RelativeLayout rootView;
    private ProgressBar horizontalProgressBar;
    private ProgressBar progressBar;
    private TextView connection;
    private LoadTask loadTask;
    private LoadDataMode loadDataMode;
    private SavedFragment savedFragment;
    private OnSaveToolbarButtonListener onSaveToolbarButtonListener;
    private NoteAdapter adapter;
    private Button backBtn;
    private Button saveBtn;
    private TextView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circleProgressBar = findViewById(R.id.progressbar_view);
        horizontalProgressBar = findViewById(R.id.horizontal_progress_bar);
        rootView = findViewById(R.id.root_view);
        progressBar = findViewById(R.id.progress_bar_circle);
        connection = findViewById(R.id.not_connection);

        preferences = getPreferences(MODE_PRIVATE);
        adapter = new NoteAdapter();

        Toolbar toolbar = findViewById(R.id.toolbar);
        backBtn = toolbar.findViewById(R.id.back_toolbar_btn);
        backBtn.setOnClickListener(this);
        saveBtn = toolbar.findViewById(R.id.save_toolbar_btn);
        saveBtn.setOnClickListener(this);
        searchView = toolbar.findViewById(R.id.search_toolbar_view);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        startMode(AppMode.NORMAL);

        loadData(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startMode(AppMode.NORMAL);
    }

    private void startMode(AppMode startAppMode){
        switch (startAppMode) {
            case NORMAL:
                searchView.setVisibility(View.VISIBLE);
                backBtn.setVisibility(View.INVISIBLE);
                saveBtn.setVisibility(View.INVISIBLE);
                break;
            case EDIT:
                searchView.setVisibility(View.INVISIBLE);
                backBtn.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.INVISIBLE);
                break;
            case SAVE:
                searchView.setVisibility(View.INVISIBLE);
                backBtn.setVisibility(View.VISIBLE);
                saveBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSelectItemToEdit() {
        startMode(AppMode.EDIT);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(backBtn)){
            onBackToolbar();
        }
        if(view.equals(saveBtn)){
            onSaveToolbar();
        }
    }

    @Override
    public void changedText(TextState textState) {
        switch (textState){
            case CURRENT:
                startMode(AppMode.EDIT);
                break;
            case CHANGED:
                startMode(AppMode.SAVE);
                break;
        }
    }

    @Override
    public void setSaveToolbarListener(OnSaveToolbarButtonListener onSaveToolbarButtonListener) {
        this.onSaveToolbarButtonListener = onSaveToolbarButtonListener;
    }

    @Override
    public NoteAdapter getAdapter() {
        return adapter;
    }

    private void onBackToolbar(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(backBtn.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        onBackPressed();
    }

    private void onSaveToolbar(){
        onSaveToolbarButtonListener.onSave();
        startMode(AppMode.EDIT);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(Constant.LOADING_DATA, true);
        outState.putInt(Constant.VISIBLE_CIRCLE_PROGRESS_BAR, circleProgressBar.getVisibility());
        outState.putInt(Constant.VISIBLE_HORIZONTAL_PROGRESS_BAR, horizontalProgressBar.getVisibility());
        outState.putInt(Constant.VISIBLE_PROGRESS_BAR, progressBar.getVisibility());
        outState.putInt(Constant.VISIBLE_CONNECTION, connection.getVisibility());
        outState.putString(Constant.LOAD_MODE, loadDataMode.toString());

        savedFragment = new SavedFragment();
        savedFragment.setLoadTask(loadTask);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(savedFragment, Constant.PROGRESS_TAG)
                .commit();

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        circleProgressBar.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_CIRCLE_PROGRESS_BAR));
        horizontalProgressBar.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_HORIZONTAL_PROGRESS_BAR));
        progressBar.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_PROGRESS_BAR));
        connection.setVisibility(
                savedInstanceState.getInt(Constant.VISIBLE_CONNECTION));
        loadDataMode = LoadDataMode.valueOf(savedInstanceState.getString(Constant.LOAD_MODE));

        FragmentManager manager = getSupportFragmentManager();
        savedFragment = (SavedFragment) manager.findFragmentByTag(Constant.PROGRESS_TAG);
        if(savedFragment != null){
            loadTask = savedFragment.getLoadTask();
            loadTask.setLauncher(this);
            loadTask.setAdapter(adapter);
        }

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        circleProgressBar.setVisibility(View.INVISIBLE);

        super.onDestroy();
    }

    @Override
    public void setStateOnlineFirstLoad(boolean isOnline) {
        if(isOnline){
            progressBar.setVisibility(View.VISIBLE);
            connection.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            connection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setStateOnlineNormalLoad(boolean showSnackBar, int progress) {
        if(showSnackBar){
            Snackbar snackbar = Snackbar.make(rootView, getResources().getText(R.string.not_connection), Snackbar.LENGTH_LONG);
            snackbar.setTextColor(getResources().getColor(R.color.black));
            snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryVariant));
            snackbar.show();
        }

        horizontalProgressBar.setProgress(progress);
    }

    @Override
    public void showProgressBar() {
        switch (loadDataMode) {
            case FIRST:
                circleProgressBar.setVisibility(View.VISIBLE);
                break;
            case NORMAL:
                horizontalProgressBar.setVisibility(View.VISIBLE);
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

                circleProgressBar.setVisibility(View.INVISIBLE);
                break;
            case NORMAL:
                horizontalProgressBar.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void showEmptyResult() {
        Toast.makeText(this, getResources().getString(R.string.not_have_data), Toast.LENGTH_SHORT)
                .show();
    }

    private void loadData(Bundle bundle){
        circleProgressBar.setVisibility(View.INVISIBLE);

        if(bundle == null || !bundle.getBoolean(Constant.LOADING_DATA)){
            if(preferences.getBoolean(Constant.FIRST_LAUNCH_APP, true)){
                loadDataMode = LoadDataMode.FIRST;
            } else {
                loadDataMode = LoadDataMode.NORMAL;
            }
            loadTask = new LoadTask(this, this);
            loadTask.setAdapter(adapter);
            loadTask.setMode(loadDataMode);
            loadTask.execute();
        }
    }
}