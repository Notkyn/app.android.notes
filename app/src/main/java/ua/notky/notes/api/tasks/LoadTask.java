package ua.notky.notes.api.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ua.notky.notes.data.model.Note;
import ua.notky.notes.data.service.NoteService;
import ua.notky.notes.data.service.NoteServiceImp;
import ua.notky.notes.gui.MainActivity;
import ua.notky.notes.gui.listener.LoadingData;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.util.NetworkUtil;
import ua.notky.notes.util.enums.LoadDataMode;

public class LoadTask extends AsyncTask<Void, Integer, Integer> {
    private LoadingData launcher;
    private NoteAdapter adapter;
    private LoadDataMode mode;
    private boolean isOnline;
    private boolean isSnackBar;
    private final NoteService noteService;

    public LoadTask(Context context, LoadingData launcher) {
        this.launcher = launcher;
        this.noteService = new NoteServiceImp(context);
        this.isSnackBar = false;
    }

    public void setAdapter(NoteAdapter adapter) {
        this.adapter = adapter;
    }

    public void setMode(LoadDataMode mode) {
        this.mode = mode;
    }

    public void setLauncher(LoadingData launcher) {
        this.launcher = launcher;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        launcher.showProgressBar();
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
                launcher.setStateOnlineFirstLoad(isOnline);
                break;
            case NORMAL:
                launcher.setStateOnlineNormalLoad(isSnackBar, values[0] * 4);
                isSnackBar = false;
                break;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        adapter.dataChanged(noteService.getAll());

        if(integer == null || integer == 0){
            launcher.showEmptyResult();
        }
        launcher.hideProgressBar();
    }

    /*
        Имитация процеса  загрузки даных:
        - проверка соединения
        - задержка - вариант ожидания ответа запроса
     */
    private List<Note> getData(){
        boolean changeConection = true;
        boolean oldConnection = NetworkUtil.isOnline((MainActivity)launcher);

        for (int i = 0; i <= 25; i++){
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            isOnline = NetworkUtil.isOnline((MainActivity)launcher);

            if(oldConnection != isOnline){
                oldConnection = isOnline;
                changeConection = true;
            }

            if(changeConection && !isOnline){
                changeConection = false;
                isSnackBar = true;
            }

            if(!isOnline){
                i = 0;
            }

            publishProgress(i);
        }
//        даные для примера
//        return DefaultDataUtil.getDefaulData();
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
