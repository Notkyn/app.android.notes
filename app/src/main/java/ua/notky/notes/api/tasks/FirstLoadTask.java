package ua.notky.notes.api.tasks;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

import ua.notky.notes.gui.listener.FirstLaunch;
import ua.notky.notes.util.PreferencesConstant;
import ua.notky.notes.util.enums.LaunchState;

public class FirstLoadTask extends AsyncTask<Void, Void, Void> {
    private final FirstLaunch launcher;

    public FirstLoadTask(FirstLaunch launcher) {
        this.launcher = launcher;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        launcher.launch(LaunchState.START);
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
        launcher.launch(LaunchState.STOP);
    }
}
