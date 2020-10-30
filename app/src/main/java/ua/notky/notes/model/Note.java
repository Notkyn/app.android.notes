package ua.notky.notes.model;

import java.util.Date;
import java.util.Objects;

public class Note extends AbstractBaseEntity{
    private String title;
    private String description;
    private Date date;


    public Note() {
    }

    public Note(Long id) {
        super(id);
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
}
