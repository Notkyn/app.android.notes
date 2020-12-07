package ua.notky.notes.util.dagger.components;

import dagger.Subcomponent;
import ua.notky.notes.util.dagger.modules.FragmentModule;
import ua.notky.notes.util.dagger.scopes.FragmentScope;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    ActivityComponent plusActivity();
}
