package com.xxm.review.fragment.animation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxm.review.R;
import com.xxm.review.view.AnimationButton;

/**
 * 按钮自定义动画
 */
public class ButtonAnimationFragment extends Fragment {

    private AnimationButton animationButton;

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
        View inflate = inflater.inflate(R.layout.fragment_button_animation, null);
        animationButton = (AnimationButton) inflate.findViewById(R.id.animation_btn);
        animationButton.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                animationButton.start();
            }

            @Override
            public void animationFinish() {
                //Toast.makeText(this,"动画执行完毕",Toast.LENGTH_SHORT).show();
//                finish();
                animationButton.reset();
            }
        });
        return inflate;
    }

}
