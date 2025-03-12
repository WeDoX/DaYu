package com.onedream.dayu;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.onedream.dayu.crash.CrashHandler;
import com.onedream.dayu.crash.imp.SaveLocalFileCrashCustomHandleListenerImp;
import com.onedream.dayu.protector.LaoYe;

public final class DaYuInitializationProvider extends ContentProvider {
    //查看Framework层的ActivityThread中的handleBindApplication方法可知：
    //1、该onCreate方法在Application的onCreate方法前执行
    //2、由于if (mContext == null)才会调用该onCreate方法，所以多进程时，也不会造成影响
    @Override
    public boolean onCreate() {
        Context applicationContext = getContext().getApplicationContext();
        //
        LaoYe.INSTANCE.protectApp((Application) applicationContext);
        //
        CrashHandler.getInstance().init(applicationContext, new SaveLocalFileCrashCustomHandleListenerImp());
        //
        return false;
    }

    @Override
    public android.database.Cursor query(
            Uri uri,
            String[] projection,
            String selection,
            String[] selectionArgs,
            String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}