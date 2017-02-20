package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditNameDialogListener, AddItemDialogFragment.AddItemDialogListener {

    private ArrayList<Note> items;
    private NoteAdapter itemsAdapter;
    private RecyclerView lvItems;
    private NoteDatabaseHelper dbHelper;
    private FloatingActionButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = NoteDatabaseHelper.getInstance(this);
        items = (ArrayList) dbHelper.getAllNotes();
        button = (FloatingActionButton) findViewById(R.id.addNewItemBtn);

        lvItems = (RecyclerView) findViewById(R.id.lvItems);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lvItems.setLayoutManager(llm);

        itemsAdapter = new NoteAdapter(this, items);
        lvItems.setAdapter(itemsAdapter);

        button.setOnClickListener(new FloatingActionButton.OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AddItemDialogFragment addItemDialogFragment = AddItemDialogFragment.newInstance();
                addItemDialogFragment.show(fm, "fragment_edit_name");
            }
        });
    }


    @Override
    public void onFinishEditDialog(String newText, int pos) {
        Note note = items.get(pos);
        note.setText(newText);
        items.set(pos, note);
        itemsAdapter.notifyDataSetChanged();

        dbHelper.updateNote(note);

    }

    @Override
    public void onAddItemDialog(String newText) {
        Note newNote = new Note();
        newNote.setText(newText);

        items.add(newNote);
        itemsAdapter.notifyDataSetChanged();
        dbHelper.addNote(newNote);

    }
}
