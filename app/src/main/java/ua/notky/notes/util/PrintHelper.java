package ua.notky.notes.util;

import android.util.Log;

public class PrintHelper {
    public static <T> void print(T t){
        Log.i("print", t + "");
    }

    public static void print(String msg){
        Log.i("print", msg);
    }


}
