package ua.notky.notes.gui.presenter.main;

public interface MainView {
    void showProgressBar();
    void showEmptyResult();
    void hideProgressBar();
    void setStateOnlineFirstLoad(boolean isOnline);
    void setStateOnlineNormalLoad(boolean showSnackBar, int progress);
}
