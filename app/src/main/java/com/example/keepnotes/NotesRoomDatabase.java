package com.example.keepnotes;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class},version = 2,exportSchema = false)
public abstract class NotesRoomDatabase extends RoomDatabase {
    public abstract NoteDao NoteDao();

    private static volatile NotesRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    };

    static NotesRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NotesRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotesRoomDatabase.class, "notes_database")
//                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            databaseWriteExecutor.execute(()->{
//                NoteDao noteDao = INSTANCE.NoteDao();
//                noteDao.insertNote(new Note("Hello","First Note"));
//                noteDao.insertNote(new Note("Sandeep","It's my name"));
//            });
//        }
//    };
}
