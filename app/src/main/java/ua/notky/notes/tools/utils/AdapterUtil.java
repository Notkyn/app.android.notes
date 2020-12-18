package ua.notky.notes.tools.utils;

import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import ua.notky.notes.model.Note;
import ua.notky.notes.tools.dagger.AppDagger;

public class AdapterUtil {

    public static LiveData<PagedList<Note>> getLivePageList(){
        return new LivePagedListBuilder<>(AppDagger.getInstance().getComponent().getFactoryDataSource(), getConfig())
                .setFetchExecutor(Executors.newSingleThreadExecutor())
                .build();
    }

    private static PagedList.Config getConfig(){
        return new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(10)
                .build();
    }
}
