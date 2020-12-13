package ua.notky.notes.util.dagger.components;

import dagger.Subcomponent;
import ua.notky.notes.util.dagger.modules.ApiModule;
import ua.notky.notes.util.dagger.scopes.ApiScope;

@ApiScope
@Subcomponent(modules = {ApiModule.class})
public interface ApiComponent {
    FragmentComponent plusFragment();
}
