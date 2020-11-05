package ua.notky.notes.api.tasks;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import ua.notky.notes.util.NetworkUtil;
import ua.notky.notes.util.PrintHelper;

public class ChechConnection extends Thread {
    private final Context context;
    private boolean stop;

    public ChechConnection(Context context) {
        this.context = context;
        this.stop = false;
    }

    @Override
    public void run() {
        while (!stop){
            PrintHelper.print("Connection - " + NetworkUtil.isOnline(context));

            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
