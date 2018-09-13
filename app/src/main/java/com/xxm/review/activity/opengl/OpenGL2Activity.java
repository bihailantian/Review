package com.xxm.review.activity.opengl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.xxm.review.R;
import com.xxm.review.opengl.STLRenderer;

public class OpenGL2Activity extends AppCompatActivity {

    private static final String TAG = OpenGL2Activity.class.getSimpleName();

    private GLSurfaceView glView;
    private float rotateDegree = 0;
    private STLRenderer glRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        boolean supperEs2 = checkSupperES2();
        Log.d(TAG, "supperEs2==" + supperEs2);

        if (supperEs2) {
            glView = new GLSurfaceView(this);
            glRenderer = new STLRenderer(this);
            glView.setRenderer(glRenderer);
            setContentView(glView);
        } else {
            setContentView(R.layout.activity_open_gl2);
            Toast.makeText(this, "当前设备不支持OpenGL ES 2.0!", Toast.LENGTH_SHORT).show();
        }
    }


    public void rotate(float degree) {
        glRenderer.rotate(degree);
        glView.invalidate();
    }

    private static final int WHAT_MSG = 0x001;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            rotateDegree += 5;
            rotate(rotateDegree);
            handler.sendEmptyMessageDelayed(WHAT_MSG, 100);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (glView != null) {
            glView.onResume();
            handler.sendEmptyMessage(WHAT_MSG);
        }
    }


    /**
     * 检测设备是否支持OpenGL
     */
    private boolean checkSupperES2() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Activity.ACTIVITY_SERVICE);
        boolean supperEs2 = false;
        if (activityManager != null) {
            ConfigurationInfo deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo();
            supperEs2 = deviceConfigurationInfo.reqGlEsVersion >= 0x2000;
        }

        //由于这段代码不能很好的在模拟器上工作（GPU模拟部分有缺陷）
        boolean isEmulator = (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86"));


        return supperEs2 || isEmulator;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (glView != null) {
            glView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (glView != null) {
            glView.onResume();
        }
        if (handler != null) {
            handler.removeMessages(WHAT_MSG);
        }
    }
}
