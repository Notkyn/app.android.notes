package ua.notky.notes.tools.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
}
