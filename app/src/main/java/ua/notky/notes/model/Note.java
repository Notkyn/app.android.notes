package ua.notky.notes.model;

import java.util.Date;
import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import ua.notky.notes.util.DateUtil;

@Entity(tableName = "note")
public class Note extends AbstractBaseEntity implements Comparable<Note>{
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "date")
    @TypeConverters(value = {DateUtil.class})
    private Date date;

    @Ignore
    public Note() {
    }

    @Ignore
    public Note(Integer id) {
        super(id);
    }

    @Ignore
    public Note(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Note(Integer id, String title, String description, Date date) {
        super(id);
        this.title = title;
        this.description = description;
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;

        Note note = (Note) obj;

        return Objects.equals(title, note.title)
                && Objects.equals(description, note.description)
                && Objects.equals(date, note.date);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + title.hashCode() + description.hashCode() + date.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", " + title +
                ", " + date +
                ", " + description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Note note) {
        return title.compareTo(note.getTitle());
    }
}
