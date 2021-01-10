package com.dnh.todolistaadc;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dnh.todolistaadc.custom_views.PriorityStarImageView;
import com.dnh.todolistaadc.data.TodoListContract;
import com.dnh.todolistaadc.utils.TodoDateUtils;

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
        cursor.moveToPosition(position);
        holder.cb.setText(cursor.getString(descIndex));
        holder.dueDate.setTextColor(holder.priority.getCurrentTextColor());

        String dueDateStr;
        long dueDate = cursor.getLong(dueDateIndex);
        if (dueDate == TodoTask.NO_DUE_DATE) {
            dueDateStr = "No due date";
        } else {
            dueDateStr = TodoDateUtils.formatDueDate(context, dueDate);
        }

        int priority = cursor.getInt(priorityIndex);
        holder.dueDate.setText(dueDateStr);
        holder.priority.setText(resources.getStringArray(R.array.priorities)[priority]);
        int isCompleted = cursor.getInt(completedIndex);
        holder.cb.setChecked(isCompleted == TodoTask.COMPLETED);

        if (isCompleted == TodoTask.COMPLETED) {
            holder.container.setBackground(resources.getDrawable(R.drawable.list_item_completed));
            holder.cb.setTextColor(resources.getColor(R.color.colorCompleted));
            holder.priority.setText(resources.getString(R.string.completed));
            priority = PriorityStarImageView.COMPLETED;
        } else {
            holder.container.setBackground(resources.getDrawable(R.drawable.list_item_touch_selector));
            holder.cb.setTextColor(resources.getColor(R.color.colorPrimaryDark));
            holder.priority.setText(resources.getStringArray(R.array.priorities)[priority]);
            if (dueDate < TodoDateUtils.getTodayDateInMillis()) {
                // display overdue tasks with the date in red
                // yeah, I know red for both overdue and high priority may be not the best idea
                holder.dueDate.setTextColor(resources.getColor(R.color.colorPrimaryDark));
            } else {
                holder.dueDate.setTextColor(holder.priority.getCurrentTextColor());
                Log.d(TAG, "color is " + (holder.priority.getCurrentTextColor()));
            }
        }
        holder.star.setPriority(priority);
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
            descIndex = cursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_DESCRIPTION);
            priorityIndex = cursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_PRIORITY);
            dueDateIndex = cursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_DUE_DATE);
            idIndex = cursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_ID);
            completedIndex =cursor.getColumnIndex(TodoListContract.TodoListEntry.COLUMN_COMPLETED);
        }
        notifyDataSetChanged();
    }

    public interface TodoListAdapterOnClickHandler {
        void onClick(TodoTask todoTask, View view);
    }

    public class TodoListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final CheckBox cb;
        final TextView dueDate;
        final TextView priority;
        final PriorityStarImageView star;
        final ConstraintLayout container;
        public TodoListAdapterViewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.todo_desc_cb);
            dueDate = itemView.findViewById(R.id.due_date_tv);
            priority = itemView.findViewById(R.id.todo_priority_tv);
            star = itemView.findViewById(R.id.todo_priority_star);
            container = itemView.findViewById(R.id.item_todo_list);
            itemView.setOnClickListener(this);
            cb.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cursor.moveToPosition(getAdapterPosition());
            TodoTask todoTask = new TodoTask(cursor.getString(descIndex),
                    cursor.getInt(priorityIndex), cursor.getLong(dueDateIndex),
                    cursor.getInt(idIndex), cursor.getInt(completedIndex));
            onClickHandler.onClick(todoTask, v);
        }
    }
}
