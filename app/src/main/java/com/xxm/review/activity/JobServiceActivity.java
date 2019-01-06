package com.xxm.review.activity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.service.JobSchedulerService;

/**
 * JobService和JobScheduler测试
 */
public class JobServiceActivity extends BaseActivity {

    private static final String TAG = JobServiceActivity.class.getSimpleName();
    private int jobId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
    }

    public void startJobSchedulerService(View view) {
        Log.d(TAG, "startJobSchedulerService");
        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(this, JobSchedulerService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobId++, componentName);
        //设置JobService执行的最小延时时间
        builder.setMinimumLatency(1000 * 3);
        //设置JobService执行的最晚时间
        builder.setOverrideDeadline(1000 * 15);
        /*
         * 联网状态（
         * NETWORK_TYPE_NONE--不需要网络，
         * NETWORK_TYPE_ANY--任何可用网络，
         * NETWORK_TYPE_UNMETERED--不按用量计费的网络）
         */
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);

        builder.setRequiresDeviceIdle(false);//是否要求设备为idle状态
        builder.setRequiresCharging(false);//是否要设备为充电状态

        if (jobScheduler != null) {
            jobScheduler.schedule(builder.build()); //至少要有一个约束，否则抛出异常
        }
    }
}
