package ua.notky.notes.tools.dagger.components;

import dagger.Subcomponent;
import ua.notky.notes.tools.dagger.modules.FragmentModule;
import ua.notky.notes.tools.dagger.scopes.FragmentScope;

@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {
    ActivityComponent plusActivity();
}
