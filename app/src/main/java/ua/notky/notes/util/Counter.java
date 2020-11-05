package ua.notky.notes.util;

import java.util.concurrent.TimeUnit;

public class Counter {
    public static void count(long period, String msg){
        for(int i = 1; i <= 10; i++){
            try {
                TimeUnit.SECONDS.sleep(period);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(msg + ": " + "i = " + i);
        }
    }
}
