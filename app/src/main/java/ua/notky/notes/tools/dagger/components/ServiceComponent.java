package ua.notky.notes.tools.dagger.components;

import dagger.Subcomponent;
import ua.notky.notes.tools.dagger.modules.ServiceModule;
import ua.notky.notes.tools.dagger.scopes.ServiceScope;

@ServiceScope
@Subcomponent(modules = {ServiceModule.class})
public interface ServiceComponent {
    ApiComponent plusApi();
}
