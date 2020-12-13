package ua.notky.notes.util.dagger.modules;

import dagger.Module;
import dagger.Provides;
import ua.notky.notes.api.AppExecutors;
import ua.notky.notes.util.dagger.scopes.ApiScope;

@Module
public class ApiModule {

    @ApiScope
    @Provides
    public AppExecutors getAppExecutors(){
        return new AppExecutors();
    }
}
