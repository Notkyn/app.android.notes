package ua.notky.notes.gui.presenter.main;

import android.content.Context;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import ua.notky.notes.gui.listener.OnSaveToolbarButtonListener;
import ua.notky.notes.tools.Constant;
import ua.notky.notes.tools.PrintHelper;
import ua.notky.notes.tools.dagger.AppDagger;
import ua.notky.notes.tools.utils.NetworkUtil;

public class MainPresenterImp implements MainPresenter {
    private MainView view;
    private boolean isLoading = false;
    @Inject Context context;
    @Inject OnSaveToolbarButtonListener onSaveToolbarButtonListener;

    public MainPresenterImp() {
        AppDagger.getInstance().getComponent().injectMainPresenter(this);
    }

    @Override
    public void save() {
        onSaveToolbarButtonListener.onSave();
    }

    @Override
    public void startLoadData() {
        if(!isLoading) {
            if(!NetworkUtil.isOnline()){
                view.showNotConnection();
            }

            OneTimeWorkRequest request = NetworkUtil.createRequest(NetworkUtil.createNetworkConstraints());
            WorkManager workManager = WorkManager.getInstance(context);
            workManager.enqueue(request);

            // результат выполнение задачи
            LiveData<WorkInfo> status = workManager.getWorkInfoByIdLiveData(request.getId());
            // наблюдаем за процесом выполнения задачи
            status.observeForever(workInfo -> {
                view.setProgressValue(workInfo.getProgress().getInt(Constant.PROGRESS, 0));

                stateLoading(workInfo);

                if(workInfo.getState().isFinished()) {
                    view.hideProgressBar();
                }

                isLoading = true;
            });
        }
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
    }

    private void stateLoading(WorkInfo workInfo){
        switch (workInfo.getState()) {
            case ENQUEUED:
                PrintHelper.print(isLoading);
                if(isLoading){
                    view.showNotConnection();
                }
                break;
            case RUNNING:
                view.showProgressBar();
                break;
            case SUCCEEDED:
                if(!workInfo.getOutputData().getBoolean(Constant.IS_DATA, false)){
                    view.showEmptyResult();
                }
                break;
        }
    }
}
