package com.xxm.review.fragment.animation;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxm.review.R;
import com.xxm.review.view.WaveViewByBezier;

/**
 * 波浪动画--贝塞尔曲线实现
 */
public class BezierCurveFragment extends Fragment {


    private WaveViewByBezier waveViewByBezier;

    public BezierCurveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AnimationFragment.
     */
    public static BezierCurveFragment newInstance() {
        BezierCurveFragment fragment = new BezierCurveFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_bezier_curve, null);
        waveViewByBezier = (WaveViewByBezier) inflate.findViewById(R.id.wave_bezier);

        waveViewByBezier.startAnimation();
        return inflate;
    }

    @Override
    public void onPause() {
        super.onPause();
        waveViewByBezier.pauseAnimation();
    }

    @Override
    public void onResume() {
        super.onResume();
        waveViewByBezier.resumeAnimation();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        waveViewByBezier.stopAnimation();
    }

}
