package com.xxm.review.fragment.animation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxm.review.R;

/**
 * 按钮自定义动画
 */
public class ButtonAnimationFragment extends Fragment {


    public ButtonAnimationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AnimationFragment.
     */
    public static ButtonAnimationFragment newInstance() {
        ButtonAnimationFragment fragment = new ButtonAnimationFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_button_animation, container, false);
    }

}
