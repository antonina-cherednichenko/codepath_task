package com.codepath.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tonya on 2/15/17.
 */

public class NoteDatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "SimpleTODODB";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_NOTES = "notes";

    // Notes Table Columns
    private static final String KEY_NOTE_ID = "id";
    private static final String KEY_NOTE_TEXT = "text";

    private static NoteDatabaseHelper sInstance;

    private static final String TAG = "NoteDatabaseHelper";

    public static synchronized NoteDatabaseHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new NoteDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private NoteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES +
                "(" +
                KEY_NOTE_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_NOTE_TEXT + " TEXT" +
                ")";
        try {
            db.execSQL(CREATE_NOTES_TABLE);
        } catch (Exception e) {
            Log.d(TAG, "Error creating database");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
            onCreate(db);
        }

    }

    // Get all posts in the database
    public List<Note> getAllNotes() {
        String NOTES_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_NOTES);
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(NOTES_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Note newNote = new Note();
                    newNote.setText(cursor.getString(cursor.getColumnIndex(KEY_NOTE_TEXT)));
                    newNote.setId(cursor.getInt(cursor.getColumnIndex(KEY_NOTE_ID)));
                    notes.add(newNote);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return notes;
    }

    public long addNote(Note note) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        int noteId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NOTE_TEXT, note.getText());
            noteId = (int) db.insertOrThrow(TABLE_NOTES, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return noteId;
    }


    public long updateNote(Note note) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        int noteId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NOTE_TEXT, note.getText());
            noteId = db.update(TABLE_NOTES, values, KEY_NOTE_ID + "= ?", new String[]{Integer.toString(note.getId())});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return noteId;
    }

    public long removeNote(Note note) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
        int noteId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NOTE_TEXT, note.getText());
            noteId = db.delete(TABLE_NOTES, KEY_NOTE_ID + "= ?", new String[]{Integer.toString(note.getId())});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return noteId;
    }


}
