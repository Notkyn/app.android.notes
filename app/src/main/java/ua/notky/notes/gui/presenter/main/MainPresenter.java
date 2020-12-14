package ua.notky.notes.gui.presenter.main;

import ua.notky.notes.tools.enums.LoadDataMode;

public interface MainPresenter {
    void save();
    void startLoadData(LoadDataMode mode);
    void setView(MainView view);
}
