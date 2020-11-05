package ua.notky.notes.api.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ua.notky.notes.data.model.Note;
import ua.notky.notes.data.service.NoteService;
import ua.notky.notes.data.service.NoteServiceImp;
import ua.notky.notes.gui.MainActivity;
import ua.notky.notes.gui.listener.LoadingData;
import ua.notky.notes.gui.recycler.NoteAdapter;
import ua.notky.notes.util.DefaultDataUtil;
import ua.notky.notes.util.NetworkUtil;
import ua.notky.notes.util.PrintHelper;
import ua.notky.notes.util.enums.LoadDataState;

public class LoadTask extends AsyncTask<Void, Void, Void> {
    private LoadingData launcher;
    private NoteAdapter adapter;
    private final NoteService noteService;
    private boolean isCancel;

    public LoadTask(Context context, LoadingData launcher) {
        this.launcher = launcher;
        this.noteService = new NoteServiceImp(context);
    }

    public void setAdapter(NoteAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        launcher.showProgressBar(LoadDataState.START);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        PrintHelper.print("Start");

        saveData(getData());

        PrintHelper.print("Stop");
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.dataChanged(noteService.getAll());
        launcher.showProgressBar(LoadDataState.STOP);
    }

    private List<Note> getData(){
        int i = 0;
        boolean isOnline = NetworkUtil.isOnline((MainActivity)launcher);


        while (isOnline || i < 25){
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
            isOnline = NetworkUtil.isOnline((MainActivity)launcher);
        }


        return DefaultDataUtil.getDefaulData();
    }

    private void saveData(List<Note> notes){
        List<Note> fromBd = noteService.getAll();
        for(Note note : notes){
            if(!fromBd.contains(note)){
                noteService.save(note);
            }
        }
    }

    public void setLauncher(LoadingData launcher) {
        this.launcher = launcher;
    }
}
