package ua.notky.notes.util.dagger.components;

import javax.inject.Singleton;

import dagger.Component;
import ua.notky.notes.util.dagger.modules.ContextModule;
import ua.notky.notes.util.dagger.modules.DaoModule;
import ua.notky.notes.util.dagger.scopes.DaoScope;

@DaoScope
@Component(modules = {DaoModule.class, ContextModule.class})
public interface DaoComponent {
    ServiceComponent plusService();
}
