package ua.notky.notes.util.dagger.components;

import javax.inject.Singleton;

import dagger.Subcomponent;
import ua.notky.notes.util.dagger.scopes.ApiScope;

@ApiScope
@Subcomponent
public interface ApiComponent {
    FragmentComponent plusFragment();
}
