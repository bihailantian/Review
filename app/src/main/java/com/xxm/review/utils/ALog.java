package com.xxm.review.utils;

import android.util.Log;

public class ALog {
    public static final boolean DEBUG = true;
    private static final String logPrefix = "Review-";

    public static void i(String TAG, String msg) {
        if (DEBUG)
            Log.i(logPrefix + TAG, msg);
    }

    public static void d(String TAG, String msg) {
        if (DEBUG)
            Log.d(logPrefix + TAG, msg);
    }

    public static void e(String TAG, String msg) {
        if (DEBUG)
            Log.e(logPrefix + TAG, msg);
    }

    public static void v(String TAG, String msg) {
        if (DEBUG)
            Log.v(logPrefix + TAG, msg);
    }

    public static void w(String TAG, String msg) {
        if (DEBUG)
            Log.v(logPrefix + TAG, msg);
    }


}
