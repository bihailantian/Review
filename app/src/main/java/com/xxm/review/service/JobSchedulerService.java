package com.xxm.review.service;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;

/**
 * JobService 需要添加权限android.permission.BIND_JOB_SERVICE（在
 *
 * <pre class="prettyprint">
 *     &#60;service android:name="MyJobService"
 *              android:permission="android.permission.BIND_JOB_SERVICE" &#62;
 *         ...
 *     &#60;/service&#62;
 * </pre>
 *
 *
 * 节点添加）
 */
public class JobSchedulerService extends JobService {

    private static final String TAG = JobSchedulerService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"onStartJob");
        Toast.makeText(this, "start job:" + params.getJobId(), Toast.LENGTH_SHORT).show();
        jobFinished(params, false);//任务执行完后调用jobFinsih通知系统释放相关资源
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG,"onStopJob");
        return false;
    }


}
