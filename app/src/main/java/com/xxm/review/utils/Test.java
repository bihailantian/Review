package com.xxm.review.utils;

import android.util.Log;

import androidx.annotation.IntRange;

public class Test {
    public static void setLogLevel(@IntRange(from = -1, to = 5) int logLevel) {
        int level = (logLevel == -1 ? Integer.MAX_VALUE : logLevel);
        Log.d("xuxm", "level=" + level);
    }

}
