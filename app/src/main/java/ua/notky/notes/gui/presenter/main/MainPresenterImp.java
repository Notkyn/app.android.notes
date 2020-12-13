package ua.notky.notes.gui.presenter.main;

import javax.inject.Inject;

import ua.notky.notes.api.tasks.LoadTask;
import ua.notky.notes.gui.listener.LoadingDataListener;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.util.dagger.AppDagger;
import ua.notky.notes.util.enums.LoadDataMode;

public class MainPresenterImp implements MainPresenter, LoadingDataListener {
    private MainView view;
    @Inject OnSaveToolbarButtonListener onSaveToolbarButtonListener;

    public MainPresenterImp() {
        AppDagger.getInstance().getComponent().injectMainPresenter(this);
    }

    @Override
    public void save() {
        onSaveToolbarButtonListener.onSave();
    }

    @Override
    public void startLoadData(LoadDataMode mode) {
        LoadTask loadTask = new LoadTask();
        loadTask.setLauncher(this);
        loadTask.setMode(mode);
        loadTask.execute();
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
    }

    @Override
    public void onPreLaunch() {
        view.showProgressBar();
    }

    @Override
    public void finishLoad() {
        view.hideProgressBar();
    }

    @Override
    public void dontLoadData() {
        view.showEmptyResult();
    }

    @Override
    public void isFirstLoad(boolean isOnline) {
        view.setStateOnlineFirstLoad(isOnline);
    }

    @Override
    public void setNormalLoad(boolean showSnackBar, int progress) {
        view.setStateOnlineNormalLoad(showSnackBar, progress);
    }
}
