package com.dnh.todolistaadc.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoListDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "todolist.db";
    private static final int DATABASE_VERSION = 3;

    public TodoListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TodoListContract.TodoListEntry.TABLE_NAME + " (" +
                TodoListContract.TodoListEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TodoListContract.TodoListEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                TodoListContract.TodoListEntry.COLUMN_PRIORITY + " INTEGER NOT NULL, " +
                TodoListContract.TodoListEntry.COLUMN_DUE_DATE + " LONG NOT NULL, " +
                TodoListContract.TodoListEntry.COLUMN_COMPLETED + " INTEGER NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TodoListContract.TodoListEntry.TABLE_NAME);
        onCreate(db);
    }
}
