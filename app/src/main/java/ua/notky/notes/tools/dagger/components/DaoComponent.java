package ua.notky.notes.tools.dagger.components;

import dagger.Component;
import ua.notky.notes.tools.dagger.modules.ContextModule;
import ua.notky.notes.tools.dagger.modules.DaoModule;
import ua.notky.notes.tools.dagger.scopes.DaoScope;

@DaoScope
@Component(modules = {DaoModule.class, ContextModule.class})
public interface DaoComponent {
    ServiceComponent plusService();
}
