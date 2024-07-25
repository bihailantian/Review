package com.xxm.review.provider;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.xxm.review.utils.ALog;

import java.io.FileNotFoundException;

public class TestProvider extends FileProvider {

    private static final String TAG = "TestProvider";

    public TestProvider() {
        super();
        ALog.d(TAG, "new TestProvider");
    }

    @Override
    public boolean onCreate() {
        ALog.d(TAG, "onCreate");
        return super.onCreate();

    }

    @Override
    public void attachInfo(@NonNull Context context, @NonNull ProviderInfo info) {
        super.attachInfo(context, info);
        ALog.d(TAG, "attachInfo");
        // https://mp.weixin.qq.com/s/WlNKdyLJZumOGHmhxJ4D0Q
        // 在handleBindApplication中makeApplication创建application后，会继续执行installContentProviders，
        // 这个方法内部执行installProvider，installProvider方法中会使用反射创建进程中的ContentProvider，
        // 也就是清单中的ContentProvider。并回调ContentProvider对象的attachInfo，
        // 在attachInfo中会将清单中的属性赋值给当前ContentProvider对象。并调用ContentProvider的onCreate方法。
        // 很多三方sdk在这里进行一些初始化操作
    }

    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        ALog.d(TAG, "query");
        return super.query(uri, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public String getType(@NonNull Uri uri) {
        ALog.d(TAG, "getType");
        return super.getType(uri);
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        ALog.d(TAG, "insert");
        return super.insert(uri, values);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        ALog.d(TAG, "update");
        return super.update(uri, values, selection, selectionArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        ALog.d(TAG, "delete");
        return super.delete(uri, selection, selectionArgs);
    }

    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        ALog.d(TAG, "openFile");
        return super.openFile(uri, mode);
    }
}
