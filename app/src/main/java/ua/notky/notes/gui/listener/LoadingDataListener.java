package ua.notky.notes.gui.listener;

import android.content.Context;

public interface LoadingDataListener {
    void onPreLaunch();
    void finishLoad();
    void dontLoadData();
    void isFirstLoad(boolean isOnline);
    void setNormalLoad(boolean showSnackBar, int progress);
}
