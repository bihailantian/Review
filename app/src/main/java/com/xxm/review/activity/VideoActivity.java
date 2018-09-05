package com.xxm.review.activity;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

/**
 * VideoView视频播放
 * 1.只支持mp4、avi、3gp格式的视频
 */
public class VideoActivity extends BaseActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnErrorListener {

    private static final String TAG = VideoActivity.class.getSimpleName();
    private static final String VIDEO_URL = "http://qiubai-video.qiushibaike.com/VGU6K0T3CDU6N7JJ_3g.mp4";
    private static final String VIDEO_URL2 = "http://qiubai-video.qiushibaike.com/YXSKWQA6N838MJC4_3g.mp4";

    private VideoView videoView;
    private ImageView ivPlay;
    private ImageView ivPause;
    private ImageView ivFullScreen;
    private ImageView ivPauseMin;
    private ImageView ivNext;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initView();

    }

    private void initView() {
        videoView = findViewById(R.id.videoView);

        ivPlay = findViewById(R.id.iv_play);
        ivPause = findViewById(R.id.iv_pause);
        ivFullScreen = findViewById(R.id.iv_full_screen);
        ivPauseMin = findViewById(R.id.iv_pause_min);
        ivNext = findViewById(R.id.iv_next);
        seekBar = findViewById(R.id.seekBar);


        ivPlay.setOnClickListener(this);
        ivPause.setOnClickListener(this);
        ivFullScreen.setOnClickListener(this);
        ivPauseMin.setOnClickListener(this);
        ivNext.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(this);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(VIDEO_URL);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 静音设置
                mp.setVolume(0f, 0f);
                //mp.start();
            }
        });
        //监听播放发生错误时候的事件。
        videoView.setOnErrorListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play: //播放
                Log.d(TAG, "播放视频");
                ivPlay.setVisibility(View.GONE);
                if (!videoView.isPlaying()) {
                    //设置了黑色背景无法看到视频，需要把背景颜色设置为透明
                    videoView.setBackgroundColor(Color.TRANSPARENT);
                    videoView.start();
                }
                break;

            case R.id.iv_pause: //暂停
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                ivPlay.setVisibility(View.VISIBLE);
                break;

            case R.id.iv_full_screen: //全屏
                break;

            case R.id.iv_pause_min: //暂停小按钮
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                ivPlay.setVisibility(View.VISIBLE);
                break;

            case R.id.iv_next: //播放下一个
                break;


        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        // TODO: 进度条监听
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (videoView.isPlaying()) {
            videoView.pause();//暂停播放
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();//停止播放。并释放资源
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
