package ua.notky.notes.dao.lite.note;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import ua.notky.notes.model.Note;
import ua.notky.notes.dao.lite.AppDBHelper;
import ua.notky.notes.util.dagger.AppDagger;

import static ua.notky.notes.dao.lite.note.NoteContract.*;

public class NoteRepositoryLiteImp implements NoteRepositoryLite {
    @Inject AppDBHelper helper;
    private SQLiteDatabase db;

    public NoteRepositoryLiteImp() {
        AppDagger.getInstance().getComponent().injectNoteRepository(this);
    }

    @Override
    public Note save(Note note) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(DESCRIPTION, note.getDescription());
        values.put(DATE, note.getDate().getTime());

        long id = db.insert(TABLE_NAME, null, values);

        db.close();

        note.setId((int) id);

        return note;
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
    public Note update(Note note) {
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TITLE, note.getTitle());
        values.put(DESCRIPTION, note.getDescription());
        values.put(DATE, note.getDate().getTime());

        db.update(TABLE_NAME, values, _ID + " = ?", new String[]{String.valueOf(note.getId())});

        db.close();

        return note;
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
