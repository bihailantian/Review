package com.xxm.review.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.xxm.review.R;
import com.xxm.review.utils.ViewTooltip;

public class ViewTipsActivity extends AppCompatActivity {

    private ImageView ivLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tips);

        ivLauncher = findViewById(R.id.iv_launcher);
        ivLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewTooltip
//                        .on(ivLauncher)
//                        .autoHide(true,1000)
//                        .corner(30)
//                        .position(ViewTooltip.Position.LEFT)
//                        .text("左边提示")
//                        .color(Color.RED)
//                        .show();


                ViewTooltip
                        .on(ivLauncher)

                        .autoHide(true, 1000)

                        .position(ViewTooltip.Position.LEFT)

                        .text("The text")

                        .textColor(Color.WHITE)
                        .color(Color.BLACK)

                        .corner(30)


                        //listeners
                        .onDisplay(new ViewTooltip.ListenerDisplay() {
                            @Override
                            public void onDisplay(View view) {

                            }
                        })
                        .onHide(new ViewTooltip.ListenerHide() {
                            @Override
                            public void onHide(View view) {

                            }
                        })
                        .show();


            }
        });
    }
}
