package com.xxm.review.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import java.util.Random;

/**
 * 验证码
 */
public class VCodeUtils {
    private static final String TAG = "VCodeUtils";
    private static final VCodeUtils ourInstance = new VCodeUtils();

    public static VCodeUtils getInstance() {
        return ourInstance;
    }

    private VCodeUtils() {
    }

    private static String verificationCode;

    // 验证码图片的宽度。
    private static int width;
    // 验证码图片的高度。
    private static int height;
    // 验证码字符个数
    private static int codeCount = 4;
    private static int x;
    // 字体高度
    //private final static int fontHeight;
    //private final static int codeY;
    private final static char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q',
            'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};

    static {

        width = 100;//DensityUtils.dp2px(SubwayApplication.getContext(), 60);
        height = 50;//DensityUtils.dp2px(SubwayApplication.getContext(), 30);

        x = width / (codeCount + 1);
    }


    public Bitmap getVCodeImg(Context context) {
        // 定义图像buffer
        Bitmap buffImg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas g = new Canvas(buffImg);
        Paint paint = new Paint();
        paint.setAntiAlias(true);//去除锯齿
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        g.drawColor(Color.BLACK);
        g.drawRect(0, 0, width, height, paint);
        // 画边框。
        paint.setColor(Color.WHITE);
        //paint.setStyle(Paint.Style.STROKE);
        g.drawRect(1, 1, width - 1, height - 1, paint);
        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
        paint.setColor(Color.BLACK);
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl, paint);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuilder randomCode = new StringBuilder();
        int red, green, blue;
        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(31)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。
            paint.setColor(Color.rgb(red, green, blue));
            paint.setTextSize(DensityUtils.sp2px(context, 22));
            Rect rect = new Rect();
            paint.getTextBounds(strRand, 0, strRand.length(), rect);
            g.drawText(strRand, i * x, (height + rect.height()) / 2f, paint);
            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
        }
        verificationCode = randomCode.toString();
        g.save();//保存
        g.restore();
        return buffImg;
    }

    public boolean verificationCode(String code) {
        return !TextUtils.isEmpty(verificationCode) && verificationCode.equalsIgnoreCase(code);
    }
}
