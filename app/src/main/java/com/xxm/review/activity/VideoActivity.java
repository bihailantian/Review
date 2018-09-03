package com.xxm.review.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

/**
 * 视频播放
 */
public class VideoActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = VideoActivity.class.getSimpleName();
    private static final String VIDEO_URL = "http://qiubai-video.qiushibaike.com/VGU6K0T3CDU6N7JJ_3g.mp4";
    private static final String VIDEO_URL2 = "http://qiubai-video.qiushibaike.com/YXSKWQA6N838MJC4_3g.mp4";

    private VideoView videoView;
    private ImageView ivPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initView();

    }

    private void initView() {
        videoView = findViewById(R.id.videoView);
        ivPlay = findViewById(R.id.iv_play);
        ivPlay.setOnClickListener(this);
        //videoView.setVideoPath(VIDEO_URL);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play:
                Log.d(TAG, "播放视频");
                ivPlay.setVisibility(View.GONE);
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                break;
        }
    }
}