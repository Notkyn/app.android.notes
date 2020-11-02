package ua.notky.notes.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ua.notky.notes.R;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.service.NoteServiceImp;

import android.os.Bundle;

import java.util.Objects;

import static ua.notky.notes.util.DefaultDataUtil.getDefaulData;

public class MainActivity extends AppCompatActivity{
    private NoteService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        noteService = new NoteServiceImp(this);

        if(noteService.getAll().size() == 0) {
            noteService.saveAll(getDefaulData());
        }
    }
}