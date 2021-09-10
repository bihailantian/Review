package com.xxm.review.activity.animation;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.fragment.animation.AnimationFragment;
import com.xxm.review.fragment.animation.BezierCurveFragment;
import com.xxm.review.fragment.animation.ButtonAnimationFragment;
import com.xxm.review.fragment.animation.CustomProgressFragment;
import com.xxm.review.fragment.animation.MountedFragment;
import com.xxm.review.fragment.animation.RevealAnimationFragment;
import com.xxm.review.fragment.animation.SinCosFragment;
import com.xxm.review.fragment.animation.StickyBallFragment;
import com.xxm.review.fragment.animation.WaterFragment;

/**
 * 动画
 */
public class AnimationActivity extends BaseActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.content, AnimationFragment.newInstance())
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_animation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.custom_progress:
                msg = getString(R.string.custom_progress);
                fragment = CustomProgressFragment.newInstance();
                break;

            case R.id.bezier_curve:
                msg = getString(R.string.bezier_curve);
                fragment = BezierCurveFragment.newInstance();
                break;


            case R.id.sin_cos:
                msg = getString(R.string.sin_cos);
                fragment = SinCosFragment.newInstance();
                break;

            case R.id.water:
                msg = getString(R.string.water);
                fragment = WaterFragment.newInstance();
                break;


            case R.id.button_animation:
                msg = getString(R.string.button_animation);
                fragment = ButtonAnimationFragment.newInstance();
                break;


            case R.id.sticky_ball:
                msg = getString(R.string.sticky_ball);
                fragment = StickyBallFragment.newInstance();
                break;


            case R.id.mounted:
                msg = getString(R.string.mounted);
                fragment = MountedFragment.newInstance();
                break;


            case R.id.reveal_animation:
                msg = getString(R.string.reveal_animation);
                fragment = RevealAnimationFragment.newInstance();
                break;

        }

        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        switchFragment(fragment);

        return super.onOptionsItemSelected(item);
    }


    private void switchFragment(Fragment fragment) {
        Logger.d(mFragmentManager.getBackStackEntryCount());

        mFragmentManager.beginTransaction().replace(R.id.content, fragment).commit();

    }
}
