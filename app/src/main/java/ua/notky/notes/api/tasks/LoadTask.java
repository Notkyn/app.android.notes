package ua.notky.notes.api.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import ua.notky.notes.api.AppExecutors;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.gui.listener.LoadingDataListener;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.tools.utils.AdapterUtil;
import ua.notky.notes.tools.utils.NetworkUtil;
import ua.notky.notes.tools.dagger.AppDagger;
import ua.notky.notes.tools.enums.LoadDataMode;
import ua.notky.notes.tools.utils.NoteUtil;

public class LoadTask extends AsyncTask<Void, Integer, Integer> {
    private LoadingDataListener launcher;
    private LoadDataMode mode;
    private boolean isOnline;
    private boolean isSnackBar;
    //todo context?
    @Inject Context context;
    @Inject NoteAdapter adapter;
    @Inject NoteService noteService;
    @Inject AppExecutors executors;

    public LoadTask() {
        this.isSnackBar = false;

        AppDagger.getInstance().getComponent().injectLoadTask(this);
    }

    public void setMode(LoadDataMode mode) {
        this.mode = mode;
    }

    public void setLauncher(LoadingDataListener launcher) {
        this.launcher = launcher;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        launcher.onPreLaunch();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        List<Note> notes = getData();
        saveData(notes);
        return notes.size();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        switch (mode) {
            case FIRST:
                launcher.isFirstLoad(isOnline);
                break;
            case NORMAL:
                launcher.setNormalLoad(isSnackBar, values[0] * 4);
                isSnackBar = false;
                break;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        executors.multiple().execute(() -> {
            List<Note> list = noteService.getAll();
            list.addAll(adapter.getList());
            NoteUtil.sortWithDate(list);

            executors.ui().execute(() -> {
                AdapterUtil.update(adapter, list);
            });
        });

        if(integer == null || integer == 0){
            launcher.emptyLoadData();
        }
        launcher.finishLoad();
    }

    /*
        Имитация процеса  загрузки даных:
        - проверка соединения
        - задержка - вариант ожидания ответа запроса
     */
    private List<Note> getData(){
        boolean changeConnection = true;
        boolean oldConnection = NetworkUtil.isOnline(context);

        for (int i = 0; i <= 25; i++){
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            isOnline = NetworkUtil.isOnline(context);

            if(oldConnection != isOnline){
                oldConnection = isOnline;
                changeConnection = true;
            }

            if(changeConnection && !isOnline){
                changeConnection = false;
                isSnackBar = true;
            }

            if(!isOnline){
                i = 0;
            }

            publishProgress(i);
        }
        return new ArrayList<>();
    }

    private void saveData(List<Note> notes){
        List<Note> fromBd = noteService.getAll();
        for(Note note : notes){
            if(!fromBd.contains(note)){
                noteService.save(note);
            }
        }
    }
}
