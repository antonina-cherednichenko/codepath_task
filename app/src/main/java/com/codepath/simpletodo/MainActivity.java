package com.codepath.simpletodo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditItemDialogFragment.EditNameDialogListener {

    private ArrayList<Note> items;
    private ArrayAdapter<Note> itemsAdapter;
    private ListView lvItems;
    private NoteDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        dbHelper = NoteDatabaseHelper.getInstance(this);
        items = (ArrayList) dbHelper.getAllNotes();
       
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }


    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();

        Note newNote = new Note();
        newNote.setText(itemText);

        itemsAdapter.add(newNote);
        dbHelper.addNote(newNote);
        etNewItem.setText("");

    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        Note removeNote = items.get(position);
                        items.remove(position);
                        dbHelper.removeNote(removeNote);
                        itemsAdapter.notifyDataSetChanged();

                        return true;
                    }
                });

        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FragmentManager fm = getSupportFragmentManager();
                        EditItemDialogFragment editNameDialogFragment = EditItemDialogFragment.newInstance(((TextView) view).getText().toString(), position);
                        editNameDialogFragment.show(fm, "fragment_edit_name");

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
}
