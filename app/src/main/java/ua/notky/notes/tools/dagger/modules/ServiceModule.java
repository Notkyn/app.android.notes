package ua.notky.notes.tools.dagger.modules;

import dagger.Module;
import dagger.Provides;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.service.NoteServiceImp;
import ua.notky.notes.tools.dagger.scopes.ServiceScope;

@Module
public class ServiceModule {

    @ServiceScope
    @Provides
    public NoteService getNoteService(){
        return new NoteServiceImp();
    }

}
