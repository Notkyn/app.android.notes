package ua.notky.notes.gui.presenter.main;

public interface MainView {
    void showProgressBar();
    void hideProgressBar();
    void showEmptyResult();
    void setProgressValue(int value);
    void showNotConnection();
}
