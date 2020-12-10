package ua.notky.notes.util.dagger.modules;

import android.content.Context;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ua.notky.notes.dao.AppDBHelper;
import ua.notky.notes.dao.AppDatabase;
import ua.notky.notes.dao.NoteDao;
import ua.notky.notes.dao.note.NoteRepositoryLite;
import ua.notky.notes.dao.note.NoteRepositoryLiteImp;
import ua.notky.notes.util.dagger.scopes.DaoScope;

@Module
public class DaoModule {
    @DaoScope
    @Provides
    public NoteRepositoryLite getNoteRepository(){
        return new NoteRepositoryLiteImp();
    }

    @DaoScope
    @Provides
    public AppDBHelper getDBHelper(Context context){
        return new AppDBHelper(context);
    }

    @DaoScope
    @Provides
    public AppDatabase getDatabase(Context context){
        return Room.databaseBuilder(context,
                AppDatabase.class, "notes")
                //todo async
                .allowMainThreadQueries()
                .build();
    }

    @DaoScope
    @Provides
    public NoteDao getDao(AppDatabase database){
        return database.noteDao();
    }
}
