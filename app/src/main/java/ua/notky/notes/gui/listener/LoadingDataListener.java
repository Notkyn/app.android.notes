package ua.notky.notes.gui.listener;

public interface LoadingDataListener {
    void onPreLaunch();
    void finishLoad();
    void emptyLoadData();
    void isFirstLoad(boolean isOnline);
    void setNormalLoad(boolean showSnackBar, int progress);
}
