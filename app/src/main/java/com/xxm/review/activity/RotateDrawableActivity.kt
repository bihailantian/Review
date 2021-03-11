package com.xxm.review.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.TargetApi
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.xxm.review.R
import java.io.IOException

class RotateDrawableActivity : AppCompatActivity(), View.OnClickListener, OnPreparedListener, OnCompletionListener {
    private val Duration = 600 // 动画时长
    private var state = AnimatorState.State_Stop //动画状态
    private var audioState = AudioState.STATE_STOP //音乐播放器状态
    private var btnPre: ImageView? = null
    private var btnPlay: ImageView? = null
    private var btnNext: ImageView? = null
    private var cdBox: ImageView? = null
    private var handerd: ImageView? = null
    private var mediaPlayer: MediaPlayer? = null
    private var flag = false //标记，控制唱片旋转
    private val names = arrayOf("demo01.mp3", "demo02.mp3")
    private var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate_drawable)
        //判断SDK版本是否大于等于19，大于就让他显示，小于就要隐藏，不然低版本会多出来一个
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true)
            //还有设置View的高度，因为每个型号的手机状态栏高度都不相同
        }
        initAllViews()
    }

    private fun prepareMusic() { //从Assets中获取音频资源
        cdBox!!.drawable.level = 0
        val fileDescriptor: AssetFileDescriptor
        try {
            fileDescriptor = this.assets.openFd(names[position])
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setOnPreparedListener(this)
            mediaPlayer!!.setOnCompletionListener(this)
            mediaPlayer!!.setDataSource(fileDescriptor.fileDescriptor, fileDescriptor.startOffset, fileDescriptor.length)
            mediaPlayer!!.prepareAsync()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @TargetApi(19)
    private fun setTranslucentStatus(on: Boolean) {
        val win = window
        val winParams = win.attributes
        val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    private fun initAllViews() {
        cdBox = findViewById(android.R.id.progress)
        handerd = findViewById(android.R.id.background)
        btnPre = findViewById(android.R.id.button1)
        btnPlay = findViewById(android.R.id.button2)
        btnNext = findViewById(android.R.id.button3)
        btnPre!!.setOnClickListener(this)
        btnPlay!!.setOnClickListener(this)
        btnNext!!.setOnClickListener(this)
        MyThread().start()
    }

    override fun onClick(view: View) { // 一切按键都必须在动画完成的状态下才能被触发
        if (state == AnimatorState.State_Stop) {
            when (view.id) {
                android.R.id.button1 -> {
                    flag = false
                    stop(-1)
                }
                android.R.id.button2 -> if (audioState != AudioState.STATE_PLAYING) {
                    if (audioState == AudioState.STATE_STOP) {
                        prepareMusic()
                    } else {
                        start()
                    }
                } else {
                    pause()
                }
                android.R.id.button3 -> {
                    flag = false
                    stop(1)
                }
                else -> {
                }
            }
        }
    }

    /**
     * 开始动画
     */
    private fun start() {
        val animator = ValueAnimator.ofInt(0, 10000)
        animator.addUpdateListener { animation ->
            val level = animation.animatedValue as Int
            handerd!!.drawable.level = level
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                state = AnimatorState.State_Playing
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                state = AnimatorState.State_Stop
                audioStart()
            }
        })
        animator.duration = Duration.toLong()
        animator.start()
    }

    private fun setAudioState(audioState: AudioState) {
        this.audioState = audioState
        if (audioState == AudioState.STATE_PLAYING) {
            btnPlay!!.setImageResource(R.drawable.selector_pause)
        } else {
            btnPlay!!.setImageResource(R.drawable.selector_play)
        }
    }

    /**
     * 暂停动画
     */
    private fun pause() {
        val animator01 = ValueAnimator.ofInt(10000, 0)
        animator01.addUpdateListener { animation ->
            val level = animation.animatedValue as Int
            handerd!!.drawable.level = level
        }
        animator01.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                state = AnimatorState.State_Playing
                audioPause()
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                state = AnimatorState.State_Stop
            }
        })
        animator01.duration = Duration.toLong()
        animator01.start()
    }

    /**
     * 停止动画 ， 主要用于切歌
     */
    private fun stop(type: Int) {
        if (audioState == AudioState.STATE_PLAYING) {
            val animator01 = ValueAnimator.ofInt(10000, 0)
            animator01.addUpdateListener { animation ->
                val level = animation.animatedValue as Int
                handerd!!.drawable.level = level
            }
            animator01.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    audioStop()
                    cdBox!!.drawable.level = 0
                    state = AnimatorState.State_Playing
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    state = AnimatorState.State_Stop
                    when (type) {
                        -1 -> audioPrevious()
                        0 -> audioStop()
                        1 -> audioNext()
                    }
                }
            })
            animator01.duration = Duration.toLong()
            animator01.start()
        } else {
            audioStop()
            when (type) {
                -1 -> audioPrevious()
                0 -> audioStop()
                1 -> audioNext()
            }
        }
    }

    override fun onCompletion(mp: MediaPlayer) {
        stop(1)
    }

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        setAudioState(AudioState.STATE_PAUSE)
        start()
    }

    /**
     * 动画状态
     */
    enum class AnimatorState {
        State_Stop, State_Playing
    }

    /**
     * 音乐停止
     */
    private fun audioStop() {
        if (null != mediaPlayer && audioState != AudioState.STATE_STOP) {
            setAudioState(AudioState.STATE_STOP)
            mediaPlayer!!.stop()
            flag = false
        }
    }

    /**
     * 音乐暂停
     */
    private fun audioPause() {
        if (mediaPlayer != null && audioState == AudioState.STATE_PLAYING) {
            setAudioState(AudioState.STATE_PAUSE)
            mediaPlayer!!.pause()
            flag = false
        }
    }

    /**
     * 音乐播放
     */
    private fun audioStart() {
        if (mediaPlayer != null && (audioState == AudioState.STATE_PAUSE || audioState == AudioState.STATE_PREPARE)) {
            setAudioState(AudioState.STATE_PLAYING)
            mediaPlayer!!.start()
            flag = true
        } else {
            if (mediaPlayer == null) {
                prepareMusic()
            }
        }
    }

    /**
     * 上一首
     */
    private fun audioPrevious() {
        if (audioState == AudioState.STATE_STOP) {
            position--
            if (position < 0) {
                position = names.size - 1
            }
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }
            prepareMusic()
        }
    }

    /**
     * 下一首
     */
    private fun audioNext() {
        if (audioState == AudioState.STATE_STOP) {
            position++
            if (position >= names.size) {
                position = 0
            }
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }
            prepareMusic()
        }
    }

    enum class AudioState {
        STATE_STOP, STATE_PAUSE, STATE_PREPARE, STATE_PLAYING
    }

    internal inner class MyThread : Thread() {
        override fun run() {
            super.run()
            try {
                while (true) {
                    sleep(50)
                    if (flag) { //只有在flag==true的情况下才会对唱片进行旋转操作
                        handler.sendMessage(Message())
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var level = cdBox!!.drawable.level
            level = level + 200
            if (level > 10000) {
                level = level - 10000
            }
            cdBox!!.drawable.level = level
        }
    }
}