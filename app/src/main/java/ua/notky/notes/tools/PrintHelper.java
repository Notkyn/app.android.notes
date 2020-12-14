package ua.notky.notes.tools;

import android.util.Log;

public class PrintHelper {
    public static <T> void print(T t){
        Log.i("print_log", t + "");
    }

    public static void print(String msg){
        Log.i("print_log", msg);
    }


}
