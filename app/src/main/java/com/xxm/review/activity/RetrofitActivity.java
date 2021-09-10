package com.xxm.review.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.domain.Repo;
import com.xxm.review.service.GitHubService;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit 框架测试
 */
public class RetrofitActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = RetrofitActivity.class.getSimpleName();
    //天气
    private String url = "https://www.sojson.com/open/api/weather/json.shtml?city=北京";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.sojson.com/open/api/")
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<ResponseBody> call = service.weatherOfCity("北京");
        HttpUrl requestUrl = call.request().url();
        Log.d(TAG, requestUrl.toString());
        Log.d(TAG, requestUrl.encodedPath());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                try {
                    //if (response != null) {
                    // Log.d(TAG, call.request().url().encodedPath());
                    ResponseBody body = response.body();

                    if (body == null) {
                        Log.d(TAG, "response==null");
                    } else {
                        Log.d(TAG, body.string());
                    }
                    //System.out.println(response.body().string());
                    // } else {

                    // }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, call.request().url().encodedPath());
                t.printStackTrace();
            }
        });
    }


    private void test() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // 支持RxJava
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Observable<Repo> weatherOfCity2 = service.weatherOfCity2();
        weatherOfCity2.subscribeOn(Schedulers.io()) // 在子线程中进行Http访问
                .observeOn(AndroidSchedulers.mainThread()) // UI线程处理返回接口
                .subscribe(new Observer<Repo>() { //订阅
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Repo repo) {

                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
