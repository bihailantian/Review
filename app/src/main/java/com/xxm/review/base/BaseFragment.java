package com.xxm.review.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    protected static final String TAG =  BaseFragment.class.getSimpleName();


    protected interface OnUIPreListener {
        public void onUIPre(BaseFragment fragment);
    }

    public String tag;
    /** fragment content view cache */
    protected View mContainer;
    private int mId;

    /**
     * 
     * Called to have the fragment instantiate its user interface view
     * 
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return Return the View for the fragment's UI
     */
    protected abstract View onInflaterContent(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    /**
     * Call before switching animation
     * 
     * @param gainSelect
     */
    protected abstract void onFragmentSeletedPre(boolean gainSelect);

    /**
     * Completely visible or invisible.
     * 
     * @param gainShow
     */
    public abstract void onFragmentShowChanged(boolean gainShow);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContainer == null) {
            mContainer = onInflaterContent(inflater, container, savedInstanceState);
        }
        return mContainer;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mContainer != null) {
            ViewGroup parent = (ViewGroup) mContainer.getParent();
            if (parent != null) {
                parent.removeView(mContainer);
            }
        }
    }

    public View getContainer() {
        return mContainer;
    }
}
