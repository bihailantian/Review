package com.xxm.review.activity.animation;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;
import com.xxm.review.fragment.animation.AnimationFragment;

/**
 * 动画
 */
public class AnimationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        getSupportFragmentManager()
                .beginTransaction()
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
        switch (item.getItemId()) {
            case R.id.custom_progress:
                msg = getString(R.string.custom_progress);
                break;

            case R.id.bezier_curve:
                msg = getString(R.string.bezier_curve);
                break;


            case R.id.sin_cos:
                msg = getString(R.string.sin_cos);
                break;

            case R.id.water:
                msg = getString(R.string.water);
                break;


            case R.id.button_animation:
                msg = getString(R.string.button_animation);
                break;


            case R.id.sticky_ball:
                msg = getString(R.string.sticky_ball);
                break;


            case R.id.mounted:
                msg = getString(R.string.mounted);
                break;


            case R.id.reveal_animation:
                msg = getString(R.string.reveal_animation);
                break;

        }

        Toast.makeText(mActivity,msg,Toast.LENGTH_SHORT).show();
        
        return super.onOptionsItemSelected(item);
    }
}
