package ua.notky.notes.dao.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ua.notky.notes.model.Note;

@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
