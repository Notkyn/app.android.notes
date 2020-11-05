package ua.notky.notes.util;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;

public class RecyclerUtil {
    private RecyclerUtil() {
    }

    public static RecyclerView createRecycler(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return recyclerView;
    }
}
