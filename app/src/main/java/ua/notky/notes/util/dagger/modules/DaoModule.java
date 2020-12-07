package ua.notky.notes.util.dagger.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.notky.notes.dao.AppDBHelper;
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
}
