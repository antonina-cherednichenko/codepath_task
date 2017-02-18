package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditNameDialogListener {

    private ArrayList<Note> items;
    private NoteAdapter itemsAdapter;
    private RecyclerView lvItems;
    private NoteDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = NoteDatabaseHelper.getInstance(this);
        items = (ArrayList) dbHelper.getAllNotes();

        lvItems = (RecyclerView) findViewById(R.id.lvItems);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvItems.setLayoutManager(llm);

        itemsAdapter = new NoteAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

    }


    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        Note newNote = new Note();
        newNote.setText(itemText);

        items.add(newNote);
        itemsAdapter.notifyDataSetChanged();
        dbHelper.addNote(newNote);
        etNewItem.setText("");

    }


    @Override
    public void onFinishEditDialog(String newText, int pos) {
        Note note = items.get(pos);
        note.setText(newText);
        items.set(pos, note);
        itemsAdapter.notifyDataSetChanged();

        dbHelper.updateNote(note);

    }
}
