package com.dnh.todolistaadc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.dnh.todolistaadc.databinding.ActivityTodoListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TodoListActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        TodoListAdapter.TodoListAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = TodoListActivity.class.getSimpleName();
    private static final int ADD_TASK_REQUEST = 1;
    private static final int EDIT_TASK_REQUEST = 2;
    private static final int ID_TODOLIST_LOADER = 2021;

    private RecyclerView recyclerView;
    private TodoListAdapter todoListAdapter;
    private ActivityTodoListBinding binding;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo_list);

        recyclerView = binding.todoListRv;
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        todoListAdapter = new TodoListAdapter(this, this);
        recyclerView.setAdapter(todoListAdapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(v -> {

        });

        sharedPreferences = getPreferences(MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        LoaderManager.getInstance(this).initLoader(ID_TODOLIST_LOADER, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return super.onCreateOptionsMenu(menu);
    }
}