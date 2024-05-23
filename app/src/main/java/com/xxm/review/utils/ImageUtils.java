package com.xxm.review.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageUtils {


    private static final String TAG = ImageUtils.class.getSimpleName();


    private ImageUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 把彩色的图片显示为灰色
     *
     * @param imageView 控件
     * @param isGray    true：显示灰色，false：显示彩色
     */
    public static void imageToGray(ImageView imageView, boolean isGray) {
        if (isGray) {
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0); // 设置饱和度
            ColorMatrixColorFilter grayColorFilter = new ColorMatrixColorFilter(cm);
            imageView.setColorFilter(grayColorFilter); // 如果想恢复彩色显示，设置为null即可
        } else {
            imageView.setColorFilter(null);
        }
    }


    /**
     * 将图片转换成Base64编码的字符串
     *
     * @param path 图片的路径
     * @return Base64编码的字符串
     */
    public static String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


    /**
     * 将Base64编码转换为图片，并保存
     *
     * @param base64Str Base64编码字符串
     * @param savePath  保存路径，需要带上图片的后缀名
     * @return true
     */
    public static boolean base64ToFile(String base64Str, String savePath) {
        byte[] data = Base64.decode(base64Str, Base64.NO_WRAP);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(savePath);
            os.write(data);
            os.flush();
            os.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * 图片的base64码字符串转Bitmap对象
     *
     * @param base64Str 图片的base64码字符串
     * @return Bitmap对象
     */
    public static Bitmap stringToBitmap(String base64Str) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(base64Str, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 保存 bitmap，调用函数时记得把文件的扩展名带上
     *
     * @param bitmap  Bitmap
     * @param bitName 文件名
     * @param path    保存路径
     */
    public static void saveBitmap(Context context, Bitmap bitmap, String bitName, String path) {
        //"/sdcard/DCIM/Camera/"

        File fileDir = new File(path);

        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File file = new File(path, bitName);
        if (file.exists()) {
            file.delete();
        }
        Log.d(TAG, file.getPath() + " 是否已存在 " + file.exists());
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 90, out)) {
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 卸载图片
     * @param imageView
     */
    public static void claerImagerView(ImageView imageView){
        if (!(imageView.getDrawable() instanceof BitmapDrawable)){
            return;
        }
        BitmapDrawable bitmapDrawable  = (BitmapDrawable) imageView.getDrawable();
        bitmapDrawable.getBitmap().recycle();
        imageView.setImageDrawable(null);
    }



    /**
     * Check the SD card
     * @return
     */
    public static boolean checkSDCardAvailable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * Save image to the SD card
     * @param photoBitmap
     * @param path
     * @param photoName
     */
    public static void savePhotoToSDCard(Bitmap photoBitmap,String path,String photoName){
        if (checkSDCardAvailable()) {
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdirs();
            }

            File photoFile = new File(path , photoName);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
//                        fileOutputStream.close();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally{
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    /**
     * 根据给定的id获取处理(倒影, 倒影渐变)过的bitmap
     * @param imageID
     * @return
     */
    private static Bitmap getInvertImage(Context context, int imageID) {
        // 获得原图
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        Bitmap sourceBitmap = BitmapFactory.decodeResource(context.getResources(), imageID, opts);

        // 倒影图片
        Matrix matrix = new Matrix();
        // 设置图片的反转为, 垂直反转
        matrix.setScale(1.0f, -1.0f);
        //	float[] values = {
        //			1.0f,	0f,		0f,
        //			0f,		-1.0f,	0f,
        //			0f,		0f,		1.0f
        //	};
        // 倒影图片
        Bitmap invertBitmap = Bitmap.createBitmap(sourceBitmap, 0, sourceBitmap.getHeight() /2,
                sourceBitmap.getWidth(), sourceBitmap.getHeight() /2, matrix, false);

        // 合成一张图片
        Bitmap resultBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),
                (int) (sourceBitmap.getHeight() * 1.5 + 5), Bitmap.Config.ARGB_8888);

        // 把原图添加到合成图片的左上角
        Canvas canvas = new Canvas(resultBitmap);		// 指定画板画在合成图片上
        canvas.drawBitmap(sourceBitmap, 0, 0, null);	// 把原图绘制到合成图上

        // 把倒影图片绘制到合成图片上
        canvas.drawBitmap(invertBitmap, 0, sourceBitmap.getHeight() + 5, null);

        Rect rect = new Rect(0, sourceBitmap.getHeight() + 5, resultBitmap.getWidth(), resultBitmap.getHeight());
        Paint paint = new Paint();

        /**
         * TileMode.CLAMP 指定渲染边界以外的控件以最后的那个颜色继续往下渲染
         */
        LinearGradient shader = new LinearGradient(0,
                sourceBitmap.getHeight() + 5, 0, resultBitmap.getHeight(), 0x70FFFFFF, 0x00FFFFFF, Shader.TileMode.CLAMP);

        // 设置为取交集模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // 指定渲染器为线性渲染器
        paint.setShader(shader);
        canvas.drawRect(rect, paint);

        return resultBitmap;
    }

    /**
     * 缩略图
     *
     * @param path
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static Bitmap thumbnail(String path, int maxWidth, int maxHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        int sampleSize = calculateInSampleSize(options, maxWidth, maxHeight);
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inPurgeable = true;
        options.inInputShareable = true;
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }
}
