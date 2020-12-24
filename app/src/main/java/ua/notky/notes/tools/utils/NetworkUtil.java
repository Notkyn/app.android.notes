package ua.notky.notes.tools.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import ua.notky.notes.api.tasks.LoadingWorker;
import ua.notky.notes.tools.dagger.AppDagger;

public class NetworkUtil {

    public static boolean isOnline()
    {
        Context context = AppDagger.getInstance().getApplicationContext();

        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static Constraints createNetworkConstraints(){
        return new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
    }

    public static OneTimeWorkRequest createRequest(Constraints constraints){
        return new OneTimeWorkRequest
                .Builder(LoadingWorker.class)
                .setConstraints(constraints)
                .build();
    }
}
