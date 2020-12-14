package ua.notky.notes.tools.dagger.components;

import dagger.Subcomponent;
import ua.notky.notes.tools.dagger.modules.ApiModule;
import ua.notky.notes.tools.dagger.scopes.ApiScope;

@ApiScope
@Subcomponent(modules = {ApiModule.class})
public interface ApiComponent {
    FragmentComponent plusFragment();
}
