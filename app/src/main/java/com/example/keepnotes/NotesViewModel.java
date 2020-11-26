package com.example.keepnotes;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    NotesRepository notesRepository;
    LiveData<List<Note>> notes;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        notesRepository = new NotesRepository(application);
        notes = notesRepository.getAllNotes();
    }

    LiveData<List<Note>> getAllNotes(){
        return notesRepository.getAllNotes();
    }

    public void insertNote(Note note){
        notesRepository.insertNote(note);
    }

    public void deleteNote(Note note){
        notesRepository.deleteNote(note);
    }

    public void updateNote(int id) {notesRepository.updateNote(id);}
}
