package com.xxm.review.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.widget.ImageView;

import com.xxm.review.R;
import com.xxm.review.base.BaseActivity;


/**
 * 图片处理：压缩、放大、转换
 */
public class ImageActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ImageView ivHandle = findViewById(R.id.iv_handle);
        ImageView ivHandle2 = findViewById(R.id.iv_handle2);
        ImageView ivHandle3 = findViewById(R.id.iv_handle3);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_circle);
        //ivHandle.setImageResource(R.mipmap.ic_circle);
        ivHandle.setImageBitmap(bitmap);
        //ImageUtils.saveBitmap(bitmap,"ic_circle.png", Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Review");
        //ImageUtils.saveBitmap(this,bitmap,"ic_circle.png", Global.ROOT);

        //缩放法压缩
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        ivHandle2.setImageBitmap(bitmap);
        //ImageUtils.saveBitmap(this,bitmap,"ic_circle_scale.png", Global.ROOT);

        //RGB_565法
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_circle, options);
        ivHandle3.setImageBitmap(bitmap);
        //ImageUtils.saveBitmap(this,bitmap,"ic_circle_rgb565.png",Global.ROOT);
    }


}
