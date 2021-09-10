package com.xxm.review.fragment.animation;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xxm.review.R;

/**
 * 揭露动画
 * <p>
 * A simple {@link Fragment} subclass.
 * Use the {@link RevealAnimationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevealAnimationFragment extends Fragment {


    public RevealAnimationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RevealAnimationFragment.
     */
    public static RevealAnimationFragment newInstance() {
        RevealAnimationFragment fragment = new RevealAnimationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reveal_animation, container, false);
    }

}
