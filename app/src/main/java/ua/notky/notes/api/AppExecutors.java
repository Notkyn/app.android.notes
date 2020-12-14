package ua.notky.notes.api;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

/* Клас для создание потоков */
public class AppExecutors{
    private static final int THREAD_POOL = 3;

    // Один поток
    private final Executor single;
    // Пул потоков
    private final Executor multiple;
    // Поток UI
    private final Executor ui;

    private AppExecutors(Executor single, Executor multiple, Executor ui) {
        this.single = single;
        this.multiple = multiple;
        this.ui = ui;
    }

    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(THREAD_POOL),
                new UiThreadExecutor());
    }

    public Executor single() {
        return single;
    }

    public Executor multiple() {
        return multiple;
    }

    public Executor ui() {
        return ui;
    }

    /* Создание Handler для обновления UI */
    private static class UiThreadExecutor implements Executor {
        private final Handler uiThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            uiThreadHandler.post(command);
        }
    }
}
