package com.hobera.app.hnreader.util;

import android.content.Context;
import android.text.format.DateUtils;

import com.hobera.app.hnreader.R;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public class AppUtils {

    public static String getDisplayedTime(Context context, long timeMillis) {
        timeMillis *= 1000;
        String timeSpan = "";
        long span = Math.max(System.currentTimeMillis() - timeMillis, 0);
        if (span >= DateUtils.YEAR_IN_MILLIS) {
            timeSpan = (span / DateUtils.YEAR_IN_MILLIS) + context.getString(R.string.year);
        } else if (span >= DateUtils.WEEK_IN_MILLIS) {
            timeSpan = (span / DateUtils.WEEK_IN_MILLIS) + context.getString(R.string.week);
        } else if (span >= DateUtils.DAY_IN_MILLIS) {
            timeSpan = (span / DateUtils.DAY_IN_MILLIS) + context.getString(R.string.day);
        } else if (span >= DateUtils.HOUR_IN_MILLIS) {
            timeSpan = (span / DateUtils.HOUR_IN_MILLIS) + context.getString(R.string.hour);
        } else {
            timeSpan = (span / DateUtils.MINUTE_IN_MILLIS) + context.getString(R.string.minute);
        }

        String displayedTime = String.format(context.getString(R.string.time), timeSpan);

        return displayedTime;
    }

    public static String getDisplayMetaData(Context context, int score,
                                            String author, long timeMillis, long[] kids) {
        String time = AppUtils.getDisplayedTime(context, timeMillis);

        int commentCount = 0;
        if (kids!= null) {
            commentCount = kids.length;
        }
        String comments = String.format("%d %s",
                commentCount,
                commentCount>1?
                        context.getString(R.string.comments):
                        context.getString(R.string.comment));

        String display = String.format(
                context.getString(R.string.story_detail),
                score, author, time, comments);

        return display;
    }

}