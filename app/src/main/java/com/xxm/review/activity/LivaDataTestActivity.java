package com.xxm.review.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.xxm.review.R;

public class LivaDataTestActivity extends AppCompatActivity {

    private static final String TAG = "LivaDataTestActivity-";

    private MutableLiveData<String> contentText = new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liva_data_test);

        TextView textView = findViewById(R.id.text);
        contentText.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d(TAG, "onChanged s=" + s);
                textView.setText(s);
            }
        });


        findViewById(R.id.btn_livedata).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                contentText.postValue("livedata:" + System.currentTimeMillis());
            }
        });

    }
}