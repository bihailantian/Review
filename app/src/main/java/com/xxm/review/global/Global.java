package com.xxm.review.global;

import android.os.Environment;

import java.io.File;

/**
 * 常量
 */

public class Global {

    public static final String ROOT = Environment.getExternalStorageDirectory().getPath() + File.separator+"Review"; //"/mnt/sdcard/Review";
}
