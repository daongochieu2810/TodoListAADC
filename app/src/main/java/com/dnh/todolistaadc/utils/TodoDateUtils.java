package com.dnh.todolistaadc.utils;

import android.content.Context;
import android.text.format.DateUtils;

import java.util.Calendar;
import java.util.Locale;

public class TodoDateUtils {
    private final static String TAG = TodoDateUtils.class.getSimpleName();

    public static long getTodayDateInMillis() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        long today = calendar.getTimeInMillis();
        return today;
    }
    public static String formatDueDate(Context context, long dueDateMillis) {
        String res = DateUtils
                .formatDateTime(context, dueDateMillis,
                        DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_MONTH |
                                DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_ABBREV_WEEKDAY | DateUtils.FORMAT_SHOW_WEEKDAY);
        return res;
    }
}
