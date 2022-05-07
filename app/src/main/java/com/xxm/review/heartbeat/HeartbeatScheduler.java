package com.xxm.review.heartbeat;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;

public abstract class HeartbeatScheduler {
    protected int timeout = 20000;
    protected int minHeart = 60;
    protected int maxHeart = 300;
    protected int step = 30;
    protected volatile boolean started = false;
    protected volatile long heartbeatSuccessTime;
    protected volatile int currentHeartType;
    public static final String HEART_TYPE_TAG = "heart_type";
    public static final String HEARTBEAT_REQUEST = "heartbeat_request";
    public static final int UNKNOWN_HEART = 0, SHORT_HEART = 1, PROBE_HEART = 2, STABLE_HEART = 3, REDUNDANCY_HEART = 4;

    protected PendingIntent createPendingIntent(Context context, int requestCode, int heartType) {
        Intent intent = new Intent();
        intent.setPackage(context.getPackageName());
        intent.setAction(HEARTBEAT_REQUEST);
        intent.putExtra(HEART_TYPE_TAG, heartType);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    protected void set(int minHeart, int maxHeart, int step) {
        this.minHeart = minHeart;
        this.maxHeart = maxHeart;
        this.step = step;
        Logger.i("set minMax:" + minHeart + ",maxHeart:" + maxHeart + ",step:" + step);
    }

    protected boolean isStarted() {
        return started;
    }

    protected abstract boolean isStabled();

    protected void setCurrentHeartType(int currentHeartType) {
        this.currentHeartType = currentHeartType;
        Logger.i("set current heart type:" + currentHeartType);
    }

    protected int getTimeout() {
        return timeout;
    }

    protected void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    protected long getHeartbeatSuccessTime() {
        return heartbeatSuccessTime;
    }

    protected void setHeartbeatSuccessTime(long heartbeatSuccessTime) {
        this.heartbeatSuccessTime = heartbeatSuccessTime;
    }

    protected abstract void start(Context context);

    protected abstract void stop(Context context);

    protected abstract void clear(Context context);

    protected abstract void adjustHeart(Context context, boolean success);

    protected abstract void startNextHeartbeat(Context context, int heartType);

    protected abstract void resetScheduledHeart(Context context);

    protected abstract void receiveHeartbeatFailed(Context context);

    protected abstract void receiveHeartbeatSuccess(Context context);

    protected abstract int getCurHeart();
}

