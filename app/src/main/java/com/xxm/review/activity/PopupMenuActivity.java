package com.xxm.review.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PopupMenuActivity extends BaseActivity {

    private ImageButton btn_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popu_menu);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });

        btn_help = findViewById(R.id.btn_help);

        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu();
            }
        });
    }

    private void showMenu() {

    }


    /**
     * 显示popupMenu菜单
     *
     * @param view popupMenu菜单要依附的view
     */
    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(mActivity, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup_simple, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // 控件每一个item的点击事件
                String content = "";
                switch (item.getItemId()) {
                    case R.id.next:
                        content = getString(R.string.menu_next);
                        break;

                    case R.id.add:
                        content = getString(R.string.menu_add);
                        break;


                    case R.id.detail:
                        content = getString(R.string.menu_detail);
                        break;


                    case R.id.del:
                        content = getString(R.string.menu_del);
                        break;
                }
                Toast.makeText(mActivity, content, Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // 控件消失时的事件
            }
        });

        setForceShowIcon(popupMenu);


    }

    /**
     * 显示icon
     *
     * @param popupMenu PopupMenu菜单
     */
    public static void setForceShowIcon(PopupMenu popupMenu) {
        try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper
                            .getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod(
                            "setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
