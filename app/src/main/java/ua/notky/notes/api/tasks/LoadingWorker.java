package ua.notky.notes.api.tasks;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import ua.notky.notes.model.Note;
import ua.notky.notes.service.NoteService;
import ua.notky.notes.tools.Constant;
import ua.notky.notes.tools.dagger.AppDagger;

public class LoadingWorker extends Worker {
    @Inject NoteService noteService;

    public LoadingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        AppDagger.getInstance().getComponent().injectLoadingWorker(this);
    }

    @NonNull
    @Override
    public Result doWork() {
        List<Note> loadList = getData();
        saveData(loadList);
        return Result.success(new Data.Builder().putBoolean(Constant.IS_DATA, loadList.size() > 0).build());
    }

    private List<Note> getData(){
        for (int i = 0; i <= 100; i++){
            setProgressAsync(new Data.Builder().putInt(Constant.PROGRESS, i).build());
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
        // get default data
//        return DefaultDataUtil.generateList();
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
