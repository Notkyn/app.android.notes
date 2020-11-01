package ua.notky.notes.activity.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import ua.notky.notes.R;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final TextView description;
    private final TextView date;

    public NoteViewHolder(View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title_note_item);
        description = itemView.findViewById(R.id.description_note_item);
        date = itemView.findViewById(R.id.date_note_item);
    }

    public void setTitle(String value) {
        title.setText(value);
    }

    public void setDescription(String value) {
        description.setText(value);
    }

    public void setDate(String value) {
        date.setText(value);
    }
}
