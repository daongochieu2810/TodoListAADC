package com.dnh.todolistaadc;

import android.os.Parcel;
import android.os.Parcelable;

public class TodoTask implements Parcelable {
    public final static int HIGH = 0;
    public final static int MEDIUM = 1;
    public final static int LOW = 2;
    public final static int COMPLETED = 1;
    public final static int NOT_COMPLETED = 0;
    public final static long NO_DUE_DATE = Long.MAX_VALUE;

    public static final Parcelable.Creator<TodoTask> CREATOR = new Parcelable.Creator<TodoTask>() {
        @Override
        public TodoTask createFromParcel(Parcel source) {
            return new TodoTask(source);
        }

        @Override
        public TodoTask[] newArray(int size) {
            return new TodoTask[size];
        }
    };

    private String description;
    private int priority;
    private long dueDate;
    private int id;
    private int completed;

    public TodoTask() {
        this.description = null;
        this.priority = 0;
        this.dueDate = 0L;
        this.id = 0;
        this.completed = 0;
    }

    public TodoTask(String description, int priority, long dueDate, int id, int completed) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.id = id;
        this.completed = completed;
    }

    public TodoTask(Parcel source) {
        description = source.readString();
        priority = source.readInt();
        dueDate = source.readLong();
        id = source.readInt();
        completed = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeInt(priority);
        dest.writeLong(dueDate);
        dest.writeInt(id);
        dest.writeInt(completed);
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public long getDueDate() {
        return dueDate;
    }

    public int getId() {
        return id;
    }

    public int getCompleted() {
        return completed;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}

