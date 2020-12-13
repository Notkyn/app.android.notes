package ua.notky.notes.dao.lite.note;

import android.provider.BaseColumns;

public class NoteContract {
    private NoteContract() {
    }

    public final static String TABLE_NAME = "note";

    public final static String _ID = BaseColumns._ID;
    public final static String TITLE = "title";
    public final static String DESCRIPTION = "description";
    public final static String DATE = "date";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE + " TEXT NOT NULL DEFAULT 0, "
            + DESCRIPTION + " TEXT NULL DEFAULT 0, "
            + DATE + " LONG NOT NULL DEFAULT 0);";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String[] PROJECTION = {
            _ID,
            TITLE,
            DESCRIPTION,
            DATE};
}
