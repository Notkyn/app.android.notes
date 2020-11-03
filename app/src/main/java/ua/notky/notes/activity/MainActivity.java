package ua.notky.notes.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ua.notky.notes.R;
import ua.notky.notes.activity.widgets.ProgressBarDialog;
import ua.notky.notes.activity.widgets.SaveFragment;
import ua.notky.notes.listener.HostActivity;
import ua.notky.notes.listener.OnChangeTextListener;
import ua.notky.notes.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.listener.OnSelectItemToEditListener;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.service.NoteServiceImp;
import ua.notky.notes.tasks.FirstLoadTask;
import ua.notky.notes.util.enums.Mode;
import ua.notky.notes.util.enums.TextState;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import static ua.notky.notes.util.DefaultDataUtil.getDefaulData;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, OnSelectItemToEditListener,
        OnChangeTextListener, HostActivity {
    private NoteService noteService;
    private FirstLoadTask firstLoadTask;
    SaveFragment saveFragment;
    private OnSaveToolbarButtonListener onSaveToolbarButtonListener;
    private Button backBtn;
    private Button saveBtn;
    private TextView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveFragment = (SaveFragment) getSupportFragmentManager().findFragmentByTag("SAVE_FRAGMENT");

        if (saveFragment == null) {
            saveFragment = new SaveFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(saveFragment, "SAVE_FRAGMENT")
                    .commit();
        }


        firstStart();
        System.out.println("CREATED");
        Toolbar toolbar = findViewById(R.id.toolbar);
        backBtn = toolbar.findViewById(R.id.back_toolbar_btn);
        backBtn.setOnClickListener(this);
        saveBtn = toolbar.findViewById(R.id.save_toolbar_btn);
        saveBtn.setOnClickListener(this);
        searchView = toolbar.findViewById(R.id.search_toolbar_view);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        startMode(Mode.NORMAL);

        noteService = new NoteServiceImp(this);

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

    private void firstStart(){
        System.out.println(getLastNonConfigurationInstance());
        if(getLastNonConfigurationInstance() == null){
            firstLoadTask = new FirstLoadTask(getSupportFragmentManager());
            firstLoadTask.execute();
        }
    }

    @Nullable
    @Override
    public Object getLastNonConfigurationInstance() {
        return firstLoadTask;
    }
}