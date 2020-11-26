package com.example.keepnotes;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private final String title;
    private final String description;
    private boolean expanded;

    public Note(String title, String description) {
        this.title = title;
        this.description = description;
        this.expanded = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean getExpanded() { return expanded;}

    public void setId(int id) {
        this.id = id;
    }

    public void setExpanded(boolean expanded) {this.expanded = expanded;}
}
