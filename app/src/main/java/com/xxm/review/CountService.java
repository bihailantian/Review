package com.xxm.review;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CountService extends Service {
    public CountService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
