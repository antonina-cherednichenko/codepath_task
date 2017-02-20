package com.codepath.simpletodo;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


/**
 * Created by tonya on 8/29/16.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> notes;
    private Context context;
    private NoteDatabaseHelper dbHelper;


    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
        dbHelper = NoteDatabaseHelper.getInstance(context);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public void onBindViewHolder(NoteViewHolder viewHolder, int i) {
        Note note = notes.get(i);
        viewHolder.text.setText(note.getText());
        viewHolder.date.setText(note.getDate());
        viewHolder.priority.setText(note.getPriority());

        switch (note.getPriority()) {
            case "High":
                viewHolder.priority.setTextColor(ContextCompat.getColor(context, R.color.high_priority));
                break;
            case "Med":
                viewHolder.priority.setTextColor(ContextCompat.getColor(context, R.color.med_priority));
                break;
            case "Low":
                viewHolder.priority.setTextColor(ContextCompat.getColor(context, R.color.low_priority));
                break;
        }
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_note, viewGroup, false);

        final NoteViewHolder holder = new NoteViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = holder.getAdapterPosition();
                //check if position exists
                if (position != RecyclerView.NO_POSITION) {
                    FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                    EditItemDialogFragment editNameDialogFragment = EditItemDialogFragment.newInstance(notes.get(position).getText(), position);
                    editNameDialogFragment.show(fm, "fragment_edit_name");
                }
            }

        });

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int position = holder.getAdapterPosition();
                //check if position exists
                if (position != RecyclerView.NO_POSITION) {
                    Note removeNote = notes.get(position);
                    notes.remove(removeNote);
                    dbHelper.removeNote(removeNote);
                    notifyDataSetChanged();
                }

                return true;
            }

        });

        return holder;

    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView date;
        public TextView priority;

        public NoteViewHolder(View v) {
            super(v);
            text = (TextView) v.findViewById(R.id.note_text);
            date = (TextView) v.findViewById(R.id.note_date);
            priority = (TextView) v.findViewById(R.id.note_priority);

        }
    }


}

