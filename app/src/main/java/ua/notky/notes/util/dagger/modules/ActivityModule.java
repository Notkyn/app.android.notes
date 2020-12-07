package ua.notky.notes.util.dagger.modules;

import dagger.Module;
import dagger.Provides;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.gui.presenter.fragment.editornote.EditorNotePresenter;
import ua.notky.notes.gui.presenter.main.MainPresenter;
import ua.notky.notes.gui.presenter.main.MainPresenterImp;
import ua.notky.notes.util.dagger.scopes.ActivityScope;

@Module
public class ActivityModule {

    @ActivityScope
    @Provides
    public MainPresenter getMainPresentr(){
        return new MainPresenterImp();
    }

    @ActivityScope
    @Provides
    public OnSaveToolbarButtonListener getOnSaveToolbarButtonListener(EditorNotePresenter presenter){
        return (OnSaveToolbarButtonListener) presenter;
    }
}
