package com.example.keepnotes;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity implements NotesAdapter.Listeners {
    NotesViewModel notesViewModel;
    RecyclerView recyclerView;
    NotesAdapter notesAdapter;
    FloatingActionButton add;
    AlertDialog.Builder builder;
    EditText head,body;
    ItemTouchHelper.SimpleCallback simpleCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        builder = new AlertDialog.Builder(this);

        recyclerView = findViewById(R.id.notesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        notesAdapter = new NotesAdapter(this);
        recyclerView.setAdapter(notesAdapter);
        initItemTouch();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        notesViewModel = new NotesViewModel(getApplication());
        notesViewModel.getAllNotes().observe(this,notes -> notesAdapter.updateList(notes));

        add = findViewById(R.id.add);
        add.setOnClickListener(view -> {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialogue_view,null);
            head = dialogView.findViewById(R.id.notesTitle);
            body = dialogView.findViewById(R.id.notesDescription);
            builder.setTitle("Enter new note")
                    .setIcon(R.mipmap.ic_launcher)
                    .setView(dialogView)
                    .setPositiveButton("Add", (dialog, id) -> {
                        String title,description;
                        title = head.getText().toString().trim();
                        description = body.getText().toString().trim();
                        notesViewModel.insertNote(new Note(title,description));
                        Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void initItemTouch() {
        simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    NotesAdapter.ViewHolder noteViewHolder = (NotesAdapter.ViewHolder) viewHolder;
                    Note note = noteViewHolder.deleteNote(viewHolder.getAdapterPosition());
                    notesViewModel.deleteNote(note);

                    Toast.makeText(MainActivity.this, "Deleted Note", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.red))
                        .addActionIcon(R.drawable.ic_baseline_delete_outline_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
    }

    @Override
    public void updateNote(Note note) {
        notesViewModel.updateNote(note.getId());
    }
}