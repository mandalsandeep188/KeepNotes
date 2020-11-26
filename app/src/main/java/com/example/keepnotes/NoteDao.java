package com.example.keepnotes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("Update Notes Set expanded = NOT expanded Where id = :id")
    void updateNote(int id);

    @Query("Select * from Notes")
    LiveData<List<Note>> getAllNotes();
}
