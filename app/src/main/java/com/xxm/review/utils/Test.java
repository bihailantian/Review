package com.xxm.review.utils;

import android.content.Context;
import android.util.Log;

import androidx.annotation.IntRange;

public class Test {
    public static void setLogLevel(@IntRange(from = -1, to = 5) int logLevel) {
        int level = (logLevel == -1 ? Integer.MAX_VALUE : logLevel);
        Log.d("xuxm", "level=" + level);
    }

    private void tets(Context context) {
        //context.startInstrumentation()
    }

   /* @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
            KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

            if (event != null) {
                Tracer.d(THIS_FILE, "Key : " + event.getKeyCode() + ", action = " + event.getAction());  //keycode = 79 ,代表是有线耳机的按键
                if (event.getKeyCode() == KeyEvent.KEYCODE_HEADSETHOOK) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        Conference activedConference = ConferencesHolder.getSpeakingConference();
                        if (activedConference != null) {
                            if (activedConference.isOnTalk()) {
                                ConferencesHolder.stopPtt(context);
                            } else {
                                ConferencesHolder.startPtt(context, 0, "0");
                            }
                        }
                    } else if (event.getAction() == KeyEvent.ACTION_UP) {
                        ConferencesHolder.stopPtt(context);
                    }
                }
            }
        }
    }*/
}
