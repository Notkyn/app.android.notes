package ua.notky.notes.util.dagger.components;

import javax.inject.Singleton;

import dagger.Subcomponent;
import ua.notky.notes.util.dagger.modules.ServiceModule;
import ua.notky.notes.util.dagger.scopes.ServiceScope;

@ServiceScope
@Subcomponent(modules = {ServiceModule.class})
public interface ServiceComponent {
    ApiComponent plusApi();
}
