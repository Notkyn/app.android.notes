package ua.notky.notes.util.dagger;

import android.app.Application;

import ua.notky.notes.util.dagger.components.AppComponent;
import ua.notky.notes.util.dagger.components.DaggerDaoComponent;
import ua.notky.notes.util.dagger.modules.ContextModule;

public class AppDagger extends Application {
    private static AppDagger INSTANCE;

    public static AppDagger getInstance(){
        return INSTANCE;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        appComponent = DaggerDaoComponent.builder()
                .contextModule(new ContextModule(INSTANCE))
                .build()
                .plusService().plusApi().plusFragment().plusActivity().plusApp();
    }

    public AppComponent getComponent(){
        return appComponent;
    }
}
