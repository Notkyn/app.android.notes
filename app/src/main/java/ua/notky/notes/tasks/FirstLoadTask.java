package ua.notky.notes.tasks;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

import androidx.fragment.app.FragmentManager;
import ua.notky.notes.activity.widgets.ProgressBarDialog;

public class FirstLoadTask extends AsyncTask<Void, Void, Void> {
    private final FragmentManager fragmentManager;
    private ProgressBarDialog progressBarDialog;

    public FirstLoadTask(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBarDialog = new ProgressBarDialog();
        progressBarDialog.show(fragmentManager, "progress_bar");
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for(int i = 1; i < 10; i++){
            System.out.println(this.getClass().getSimpleName() + " - " + i);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressBarDialog.dismiss();
    }
}
