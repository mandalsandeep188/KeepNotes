package com.example.keepnotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    List<Note> notes;
    Listeners listeners;

    public NotesAdapter(Listeners listeners) {
        this.notes = new ArrayList<>();
        this.listeners = listeners;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.title.setText(note.getTitle());
        holder.description.setText(note.getDescription());

        if(holder.description.getVisibility() == View.GONE)
            holder.description.setVisibility(View.VISIBLE);

        if(note.getDescription().isEmpty() || !note.getExpanded()){
            holder.description.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void updateList(List<Note> newList){
        Log.d("Expand", "updateList: ");
        notes.clear();
        notes.addAll(newList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noteTitle);
            description = itemView.findViewById(R.id.description);
            itemView.setOnClickListener(view -> {
                Note note = notes.get(getAdapterPosition());
                note.setExpanded(!note.getExpanded());
                notifyItemChanged(getAdapterPosition());
                listeners.updateNote(note);
            });
        }
        public Note deleteNote(int position){
            return notes.get(position);
        }
    }

    public interface Listeners{
        void updateNote(Note note);
    }
}
