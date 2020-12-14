package ua.notky.notes.tools.dagger.components;

import dagger.Subcomponent;
import ua.notky.notes.tools.dagger.scopes.ActivityScope;
import ua.notky.notes.tools.dagger.modules.ActivityModule;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    AppComponent plusApp();
}
