package com.dnh.todolistaadc.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TodoListProvider extends ContentProvider {
    public static final int CODE_TASKS = 100;
    public static final int CODE_TASK_WITH_ID = 101;
    // tasks are sorted first by completion, then by the selected sort order, then by the other
    // sort order, then by the description
    public static final String SORT_ORDER_PRIORITY = TodoListContract.TodoListEntry.COLUMN_COMPLETED + ", " +
            TodoListContract.TodoListEntry.COLUMN_PRIORITY + ", " +
            TodoListContract.TodoListEntry.COLUMN_DUE_DATE + ", " +
            TodoListContract.TodoListEntry.COLUMN_DESCRIPTION;
    public static final String SORT_ORDER_DUEDATE = TodoListContract.TodoListEntry.COLUMN_COMPLETED + ", " +
            TodoListContract.TodoListEntry.COLUMN_DUE_DATE + ", " +
            TodoListContract.TodoListEntry.COLUMN_PRIORITY + ", " +
            TodoListContract.TodoListEntry.COLUMN_DESCRIPTION;
    private static UriMatcher uriMatcher = buildUriMatcher();
    private TodoListDbHelper todoListDbHelper;

    @Override
    public boolean onCreate() {
        todoListDbHelper = new TodoListDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case CODE_TASKS:
                cursor = todoListDbHelper.getReadableDatabase()
                        .query(TodoListContract.TodoListEntry.TABLE_NAME,
                                projection, selection,
                                selectionArgs, null,
                                null, sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = todoListDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case CODE_TASKS:
                long id = db.insert(TodoListContract.TodoListEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(TodoListContract.TodoListEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = todoListDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int tasksDeleted;
        switch (match) {
            case CODE_TASK_WITH_ID:
            case CODE_TASKS:
                tasksDeleted = db.delete(TodoListContract.TodoListEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = todoListDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int tasksUpdated;
        switch (match) {
            case CODE_TASK_WITH_ID:
                tasksUpdated = db.update(TodoListContract.TodoListEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return tasksUpdated;
    }

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TodoListContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, TodoListContract.PATH_TODOLIST, CODE_TASKS);
        matcher.addURI(authority, TodoListContract.PATH_TODOLIST + "/#", CODE_TASK_WITH_ID);
        return matcher;
    }
}
