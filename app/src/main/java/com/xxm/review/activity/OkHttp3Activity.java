package com.xxm.review.activity;

import android.os.Bundle;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp3 源码学习
 */
public class OkHttp3Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp3);

        test();

    }

    OkHttpClient client = new OkHttpClient();
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();

    }


     public void test() {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
    }
}
