package ua.notky.notes.util.dagger.components;

import dagger.Subcomponent;
import ua.notky.notes.util.dagger.scopes.ActivityScope;
import ua.notky.notes.util.dagger.modules.ActivityModule;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    AppComponent plusApp();
}
