package ua.notky.notes.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ua.notky.notes.R;
import ua.notky.notes.api.service.LoaderData;
import ua.notky.notes.gui.fragment.SavedFragment;
import ua.notky.notes.gui.widgets.ProgressBarDialog;
import ua.notky.notes.gui.listener.FirstLaunch;
import ua.notky.notes.gui.listener.HostActivity;
import ua.notky.notes.gui.listener.OnChangeTextListener;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.gui.listener.OnSelectItemToEditListener;
import ua.notky.notes.data.service.NoteService;
import ua.notky.notes.data.service.NoteServiceImp;
import ua.notky.notes.api.tasks.FirstLoadTask;
import ua.notky.notes.util.PreferencesConstant;
import ua.notky.notes.util.enums.LaunchState;
import ua.notky.notes.util.enums.Mode;
import ua.notky.notes.util.enums.TextState;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import static ua.notky.notes.util.DefaultDataUtil.getDefaulData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnSelectItemToEditListener,
        OnChangeTextListener, HostActivity, FirstLaunch {
    private SharedPreferences preferences;
    private ProgressBarDialog progressBarDialog;
    private SavedFragment savedFragment;
    private OnSaveToolbarButtonListener onSaveToolbarButtonListener;
    private Button backBtn;
    private Button saveBtn;
    private TextView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getPreferences(MODE_PRIVATE);
        //loadData();
        startService();

        Toolbar toolbar = findViewById(R.id.toolbar);
        backBtn = toolbar.findViewById(R.id.back_toolbar_btn);
        backBtn.setOnClickListener(this);
        saveBtn = toolbar.findViewById(R.id.save_toolbar_btn);
        saveBtn.setOnClickListener(this);
        searchView = toolbar.findViewById(R.id.search_toolbar_view);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        startMode(Mode.NORMAL);

        NoteService noteService = new NoteServiceImp(this);

        if(noteService.getAll().size() == 0) {
            noteService.saveAll(getDefaulData());
        }
    }

    private void startService(){
        startService(new Intent(this, LoaderData.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startMode(Mode.NORMAL);
    }

    private void startMode(Mode startMode){
        switch (startMode) {
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
        startMode(Mode.EDIT);
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
                startMode(Mode.EDIT);
                break;
            case CHANGED:
                startMode(Mode.SAVE);
                break;
        }
    }

    @Override
    public void setSaveToolbarListener(OnSaveToolbarButtonListener onSaveToolbarButtonListener) {
        this.onSaveToolbarButtonListener = onSaveToolbarButtonListener;
    }

    private void onBackToolbar(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(backBtn.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        onBackPressed();
    }

    private void onSaveToolbar(){
        onSaveToolbarButtonListener.onSave();
        startMode(Mode.EDIT);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        System.out.println("onSaveInstanceState");
        outState.putString(PreferencesConstant.LOADING_DATA, "LOADING!!!");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        System.out.println("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        System.out.println("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        System.out.println("onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        System.out.println("onStart");
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        System.out.println("onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        System.out.println("onResume");
        super.onResume();
    }

    @Override
    public void launch(LaunchState state) {
        switch (state) {
            case START:
                startProgressBar();
                break;
            case STOP:
                stopProgressBar();
                break;
        }
    }

    private void loadData(){
        if(preferences.getBoolean(PreferencesConstant.FIRST_LAUNCH_APP, true)){
            FirstLoadTask firstLoadTask = new FirstLoadTask(this);
            firstLoadTask.execute();
            savedFragment = new SavedFragment();
            savedFragment.setFirstLoadTask(firstLoadTask);
        }
    }

    private void startProgressBar(){
        progressBarDialog = new ProgressBarDialog();
        progressBarDialog.show(getSupportFragmentManager(), "progress_bar");
    }

    private void stopProgressBar(){
        Editor editor = preferences.edit();
        editor.putBoolean(PreferencesConstant.FIRST_LAUNCH_APP, false);
        editor.apply();
        progressBarDialog.dismiss();
    }
}