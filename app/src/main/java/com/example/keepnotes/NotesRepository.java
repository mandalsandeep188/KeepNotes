package com.example.keepnotes;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesRepository {
    private final NoteDao noteDao;
    private LiveData<List<Note>> notes;

    NotesRepository(Application application){
        NotesRoomDatabase db = NotesRoomDatabase.getDatabase(application);
        noteDao = db.NoteDao();
    }

    LiveData<List<Note>> getAllNotes(){
        return noteDao.getAllNotes();
    }

    void insertNote(Note note){
        NotesRoomDatabase.databaseWriteExecutor.execute(()->{
            noteDao.insertNote(note);
        });
    }

    void deleteNote(Note note){
        NotesRoomDatabase.databaseWriteExecutor.execute(()->{
            noteDao.deleteNote(note);
        });
    }

    void updateNote(int id){
        NotesRoomDatabase.databaseWriteExecutor.execute(()->{
            noteDao.updateNote(id);
        });
    }
}
