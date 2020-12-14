package ua.notky.notes.tools.dagger.modules;

import dagger.Module;
import dagger.Provides;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.gui.presenter.fragment.editornote.EditorNotePresenter;
import ua.notky.notes.gui.presenter.main.MainPresenter;
import ua.notky.notes.gui.presenter.main.MainPresenterImp;
import ua.notky.notes.tools.dagger.scopes.ActivityScope;

@Module
public class ActivityModule {

    @ActivityScope
    @Provides
    public MainPresenter getMainPresenter(){
        return new MainPresenterImp();
    }

    @ActivityScope
    @Provides
    public OnSaveToolbarButtonListener getOnSaveToolbarButtonListener(EditorNotePresenter presenter){
        return (OnSaveToolbarButtonListener) presenter;
    }
}
