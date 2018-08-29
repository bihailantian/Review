package com.xxm.review.activity;

import android.os.Bundle;
import android.widget.VideoView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

/**
 * 视频播放
 */
public class VideoActivity extends BaseActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.videoView);
    }
}
