package com.xxm.review.fragment.animation;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xxm.review.R;
import com.xxm.review.view.CircleProgressBarView;
import com.xxm.review.view.HorizontalProgressBar;
import com.xxm.review.view.LoadingLineView;
import com.xxm.review.view.LoadingView;
import com.xxm.review.view.ProductProgressBar;

/**
 * 自定义进度条
 */
public class CustomProgressFragment extends Fragment {

    private CircleProgressBarView circleProgressBarView;
    private HorizontalProgressBar horizontalProgressBar;
    private ProductProgressBar productProgressBar;
    private LoadingView loadingView;
    private TextView textView;
    private LoadingLineView loadingLineView;
    private Button button;


    public CustomProgressFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AnimationFragment.
     */
    public static CustomProgressFragment newInstance() {
        CustomProgressFragment fragment = new CustomProgressFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflate = inflater.inflate(R.layout.fragment_custom_progress, null);
        circleProgressBarView = inflate.findViewById(R.id.circle_progress_view);
        horizontalProgressBar = (HorizontalProgressBar) inflate.findViewById(R.id.horizontal_progress_view);
        productProgressBar = (ProductProgressBar) inflate.findViewById(R.id.product_progress_view);
        loadingView = (LoadingView) inflate.findViewById(R.id.loading_view);
        loadingLineView = (LoadingLineView) inflate.findViewById(R.id.loading_line_view);

        textView = (TextView) inflate.findViewById(R.id.progress_tv);
        button = (Button) inflate.findViewById(R.id.startAnimationBtn);



        circleProgressBarView.setProgressWithAnimation(60);
        circleProgressBarView.setProgressListener(new CircleProgressBarView.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
                textView.setText("当前进度：" + currentProgress);
            }
        });
        circleProgressBarView.startProgressAnimation();

        horizontalProgressBar.setProgressWithAnimation(60).setProgressListener(new HorizontalProgressBar.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
            }
        });
        horizontalProgressBar.startProgressAnimation();

        productProgressBar.setProgress(60).setProgressListener(new ProductProgressBar.ProgressListener() {
            @Override
            public void currentProgressListener(float currentProgress) {
                Log.e("allen", "currentProgressListener: " + currentProgress);
            }
        });

        loadingView.startAnimation();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingLineView.stopLoading();
                loadingView.startAnimation();
                horizontalProgressBar.setProgressWithAnimation(100);
                productProgressBar.setProgress(100);
                circleProgressBarView.setProgressWithAnimation(60).startProgressAnimation();
                circleProgressBarView.setProgressListener(new CircleProgressBarView.ProgressListener() {
                    @Override
                    public void currentProgressListener(float currentProgress) {
                        textView.setText("当前进度：" + currentProgress);
                    }
                });
            }
        });

        return inflate;
    }



    @Override
    public void onResume() {
        super.onResume();
        circleProgressBarView.resumeProgressAnimation();
    }

    @Override
    public void onPause() {
        super.onPause();
        circleProgressBarView.pauseProgressAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        circleProgressBarView.stopProgressAnimation();
    }
}
