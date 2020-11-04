package ua.notky.notes.gui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ua.notky.notes.R;
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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;

import static ua.notky.notes.util.DefaultDataUtil.getDefaulData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnSelectItemToEditListener,
        OnChangeTextListener, HostActivity, FirstLaunch {
    private SharedPreferences preferences;
    private LinearLayout progressBarView;
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
        progressBarView = findViewById(R.id.progressbar_view);
        loadData(savedInstanceState);


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
        outState.putBoolean(PreferencesConstant.LOADING_DATA, true);
        outState.putInt(PreferencesConstant.VISIBLE_VIEW, progressBarView.getVisibility());
        System.out.println("onSaveInstanceState: " + progressBarView.getVisibility());
        progressBarView.setVisibility(View.INVISIBLE);
        System.out.println("Hash onSaveInstanceState: " + progressBarView.hashCode());
        System.out.println("Hash onSaveInstanceState Activity: " + this.hashCode());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        if(savedInstanceState != null){
            System.out.println("Is not null");
            System.out.println("Bundle: " + savedInstanceState.getInt(PreferencesConstant.VISIBLE_VIEW));
            progressBarView.setVisibility(savedInstanceState.getInt(PreferencesConstant.VISIBLE_VIEW));
        } else {
            progressBarView.setVisibility(View.INVISIBLE);
        }
        System.out.println("Hash onRestoreInstanceState: " + progressBarView.hashCode());
        System.out.println("Hash onRestoreInstanceState Activity: " + this.hashCode());
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void launch(LaunchState state) {
        switch (state) {
            case START:
                System.out.println("Start mode");
                progressBarView.setVisibility(View.VISIBLE);
                break;
            case STOP:
                System.out.println("Stop mode");
                Editor editor = preferences.edit();
                editor.putBoolean(PreferencesConstant.FIRST_LAUNCH_APP, false);
                editor.apply();
                progressBarView.setVisibility(View.INVISIBLE);
                System.out.println("Hash STOP: " + progressBarView.hashCode());
                System.out.println("Hash STOP Activity: " + this.hashCode());
                break;
        }
    }

    private void loadData(Bundle bundle){
        progressBarView.setVisibility(View.INVISIBLE);
        System.out.println("Hash loadData: " + progressBarView.hashCode());

        if(bundle == null || !bundle.getBoolean(PreferencesConstant.LOADING_DATA)){
            if(preferences.getBoolean(PreferencesConstant.FIRST_LAUNCH_APP, true)){
                System.out.println("First!");
                FirstLoadTask firstLoadTask = new FirstLoadTask(this);
                firstLoadTask.execute();
            } else {
                System.out.println("Second!");
                FirstLoadTask firstLoadTask = new FirstLoadTask(this);
                firstLoadTask.execute();
            }
        }
    }
}