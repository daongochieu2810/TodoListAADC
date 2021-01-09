package com.dnh.todolistaadc.custom_views;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.dnh.todolistaadc.R;

public class PriorityStarImageView extends AppCompatImageView {
    public final static int HIGH = 0;
    public final static int MEDIUM = 1;
    public final static int LOW = 2;
    public final static int COMPLETED = 3;

    public PriorityStarImageView(Context context) {
        super(context);
    }

    public PriorityStarImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }
    private void style(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.PriorityStarImageView);
        int priority = attributes.getInteger(R.styleable.PriorityStarImageView_priority, HIGH);
        setPriority(priority);
        attributes.recycle();
    }
    public void setPriority(int priority) {
        Resources res = getResources();
        int star = 0;
        int contentDesc = 0;
        switch (priority) {
            case HIGH:
                star = R.drawable.ic_baseline_red_24;
                contentDesc = R.string.red_star_title;
            case MEDIUM:
                star = R.drawable.ic_baseline_yellow_24;
                contentDesc = R.string.yellow_star_title;
            case LOW:
                star = R.drawable.ic_baseline_orange_24;
                contentDesc = R.string.orange_star_title;
            case COMPLETED:
                star = R.drawable.ic_baseline_grey_24;
                contentDesc = R.string.grey_star_title;
        }
        setBackground(res.getDrawable(star));
        setContentDescription(res.getString(contentDesc));
    }
}
