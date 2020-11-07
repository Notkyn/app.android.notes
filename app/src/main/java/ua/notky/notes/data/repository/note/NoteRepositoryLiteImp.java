package ua.notky.notes.data.repository.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.notky.notes.data.model.Note;
import ua.notky.notes.data.repository.AppDBHelper;

import static ua.notky.notes.data.repository.note.NoteContract.*;

public class NoteRepositoryLiteImp implements NoteRepositoryLite {
    private final AppDBHelper helper;
    private SQLiteDatabase db;

    public NoteRepositoryLiteImp(Context context) {
        this.helper = new AppDBHelper(context);
    }

    @Override
    public void save(Note note) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(DESCRIPTION, note.getDescription());
        values.put(DATE, note.getDate().getTime());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @Override
    public boolean delete(int id) {
        db = helper.getWritableDatabase();

        int result = db.delete(TABLE_NAME, _ID + " = ?", new String[]{String.valueOf(id)});

        db.close();

        return result > 0;
    }

    @Override
    public Note get(int id) {
        db = helper.getReadableDatabase();

        try(Cursor cursor = db.query(TABLE_NAME,
                PROJECTION,
                null,
                null,
                null,
                null,
                null)){
            int indexId = cursor.getColumnIndex(_ID);
            int indexTitle = cursor.getColumnIndex(TITLE);
            int indexDescription = cursor.getColumnIndex(DESCRIPTION);
            int indexDate = cursor.getColumnIndex(DATE);

            while (cursor.moveToNext()){

                int currentId = cursor.getInt(indexId);
                if(currentId == id){
                    Note note = new Note(currentId);
                    note.setTitle(cursor.getString(indexTitle));
                    note.setDescription(cursor.getString(indexDescription));
                    note.setDate(new Date(cursor.getLong(indexDate)));

                    db.close();
                    return note;
                }
            }
        }

        db.close();
        return null;
    }

    @Override
    public void update(Note note) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(DESCRIPTION, note.getDescription());
        values.put(DATE, note.getDate().getTime());

        db.update(TABLE_NAME, values, _ID + " = ?", new String[]{String.valueOf(note.getId())});

        db.close();
    }

    @Override
    public List<Note> getAll() {
        List<Note> list = new ArrayList<>();

        db = helper.getReadableDatabase();

        try (Cursor cursor = db.query(TABLE_NAME,
                PROJECTION,
                null,
                null,
                null,
                null,
                null)) {
            int indexId = cursor.getColumnIndex(_ID);
            int indexTitle = cursor.getColumnIndex(TITLE);
            int indexDescription = cursor.getColumnIndex(DESCRIPTION);
            int indexDate = cursor.getColumnIndex(DATE);

            while (cursor.moveToNext()) {
                Note note = new Note(cursor.getInt(indexId));
                note.setTitle(cursor.getString(indexTitle));
                note.setDescription(cursor.getString(indexDescription));
                note.setDate(new Date(cursor.getLong(indexDate)));

                list.add(note);
            }
        }

        db.close();
        return list;
    }
}
