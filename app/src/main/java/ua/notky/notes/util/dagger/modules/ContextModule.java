package ua.notky.notes.util.dagger.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.notky.notes.util.dagger.scopes.DaoScope;

@Module()
public class ContextModule {
    Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @DaoScope
    @Provides
    public Context getContext(){ return context.getApplicationContext(); }
}
