package com.dnh.todolistaadc;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.TodoListAdapterViewHolder> {
    private final static String TAG = TodoListAdapter.class.getSimpleName();
    private final TodoListAdapterOnClickHandler onClickHandler;
    private Context context;
    private Resources resources;
    private Cursor cursor;
    private int descIndex;
    private int priorityIndex;
    private int dueDateIndex;
    private int idIndex;
    private int completedIndex;
    private ColorStateList completedCheckboxColors;
    private ColorStateList unCompletedCheckboxColors;


    public TodoListAdapter(Context context, TodoListAdapterOnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
        this.context = context;
        this.resources = context.getResources();

        completedCheckboxColors = new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_checked},
                        new int[] {android.R.attr.state_checked}
                },
                new int[] {
                        Color.DKGRAY,
                        resources.getColor(R.color.purple_200)
                }
        );
        unCompletedCheckboxColors = new ColorStateList(
                new int[][] {
                        new int[] {-android.R.attr.state_checked},
                        new int[] {android.R.attr.state_checked}
                },
                new int[] {
                        Color.DKGRAY,
                        resources.getColor(R.color.black)
                }
        );
    }

    @NonNull
    @Override
    public TodoListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_list, parent, false);
        return new TodoListAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        if (cursor == null) {
            return 0;
        }
        return cursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        this.cursor = cursor;
        if (cursor != null) {
            //descIndex = cursor.getColumnIndex()
        }
    }

    public interface TodoListAdapterOnClickHandler {
        void onClick(TodoTask todoTask, View view);
    }

    public class TodoListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TodoListAdapterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
