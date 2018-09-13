package com.xxm.review.activity;

import android.os.Bundle;
import android.view.View;

import com.xxm.review.R;
import com.xxm.review.activity.opengl.OpenGL1Activity;
import com.xxm.review.activity.opengl.OpenGL2Activity;
import com.xxm.review.base.BaseActivity;

/**
 * Android OpenGL
 */
public class OpenGLActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gl);
    }

    public void openGLAct(View view) {
        switch (view.getId()) {
            case R.id.btn_gl_triangle:
                actIntent(OpenGL1Activity.class);
                break;

            case R.id.btn_gl_stl:
                actIntent(OpenGL2Activity.class);
                break;
        }

    }
}
