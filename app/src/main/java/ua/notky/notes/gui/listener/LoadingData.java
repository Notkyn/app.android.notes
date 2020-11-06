package ua.notky.notes.gui.listener;

public interface LoadingData {
    void showProgressBar();
    void hideProgressBar();
    void showEmptyResult();
    void setStateOnlineFirstLoad(boolean isOnline);
    void setStateOnlineNormalLoad(boolean showSnackBar, int progress);
}
