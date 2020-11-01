package ua.notky.notes.activity;

import androidx.appcompat.app.AppCompatActivity;
import ua.notky.notes.R;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.service.NoteServiceImp;

import android.os.Bundle;

import static ua.notky.notes.util.DefaultDataUtil.getDefaulData;

public class MainActivity extends AppCompatActivity {
    private NoteService noteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        noteService = new NoteServiceImp(this);

        if(noteService.getAll().size() == 0) {
            noteService.saveAll(getDefaulData());
        }
    }
}