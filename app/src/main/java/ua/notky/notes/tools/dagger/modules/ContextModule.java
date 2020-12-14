package ua.notky.notes.tools.dagger.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ua.notky.notes.tools.dagger.scopes.DaoScope;

@Module()
public class ContextModule {
    final Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @DaoScope
    @Provides
    public Context getContext(){ return context.getApplicationContext(); }
}
