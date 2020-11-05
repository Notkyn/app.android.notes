package ua.notky.notes.gui.listener;

import ua.notky.notes.util.enums.LoadDataState;

public interface LoadingData {
    void showProgressBar(LoadDataState state);
}
