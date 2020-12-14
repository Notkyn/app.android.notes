package ua.notky.notes.tools.dagger.modules;

import dagger.Module;
import dagger.Provides;
import ua.notky.notes.api.AppExecutors;
import ua.notky.notes.tools.dagger.scopes.ApiScope;

@Module
public class ApiModule {

    @ApiScope
    @Provides
    public AppExecutors getAppExecutors(){
        return new AppExecutors();
    }
}
