package com.xxm.review.activity.opengl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.opengl.GLRenderer;

public class OpenGL1Activity extends BaseActivity {

    private static final String TAG = OpenGL1Activity.class.getSimpleName();

    private GLSurfaceView glSurfaceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_open_gl);

        boolean supperEs2 = checkSupperES2();
        Log.d(TAG, "supperEs2==" + supperEs2);

        if (supperEs2) {
            glSurfaceView = new GLSurfaceView(this);
            glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            glSurfaceView.setRenderer(new GLRenderer());
            setContentView(glSurfaceView);
        } else {
            setContentView(R.layout.activity_open_gl1);
            Toast.makeText(this, "当前设备不支持OpenGL ES 2.0!", Toast.LENGTH_SHORT).show();
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
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }
    }
}
