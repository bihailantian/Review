package com.xxm.review.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.service.GitHubService;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
}
