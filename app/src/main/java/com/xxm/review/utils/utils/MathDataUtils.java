package com.xxm.review.utils.utils;

import java.text.NumberFormat;

/**
 * 数据工具类
 */
public class MathDataUtils {

    /**
     * 保留小数
     *
     * @param f     对象
     * @param digit 保留小数位数
     * @return 字符串
     */
    public static String keepDecimalDigits(Float f, int digit) {
        String result = "";
        if (f != null) {
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMaximumFractionDigits(digit);
            nf.setGroupingUsed(false);//不按照千分位输入
            result = nf.format(f);
        }
        return result;
    }

    /**
     * 保留小数
     *
     * @param f     对象
     * @param digit 保留小数位数
     * @return 字符串
     */
    public static String keepDecimalDigits(float f, int digit) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(digit);
        nf.setGroupingUsed(false);//不按照千分位输入
        return nf.format(f);


    }


    /**
     * 去掉整数后的零
     *
     * @param f
     * @return
     */
    public static String formatValue(Float f) {
        String result = "";
        if (f == null) {
            result = "";
        } else if (f - f.intValue() == 0) {  //小数部分等于0 返回int
            result = String.valueOf(f.intValue());
        } else {
            result = String.format("%.2f", f);
        }
        return result;
    }


}
