package com.onedream.dayu

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.onedream.dayu.crash.CrashHandler
import com.onedream.dayu.crash.imp.SaveLocalFileCrashCustomHandleListenerImp
import com.onedream.dayu.protector.LaoYe

class DaYuInitializationProvider : ContentProvider() {
    //查看Framework层的ActivityThread中的handleBindApplication方法可知：
    //1、该onCreate方法在Application的onCreate方法前执行
    //2、由于if (mContext == null)才会调用该onCreate方法，所以多进程时，也不会造成影响
    override fun onCreate(): Boolean {
        val applicationContext = context!!.applicationContext
        //
        LaoYe.protectApp((applicationContext as Application))
        //
        CrashHandler.getInstance()
            .init(applicationContext, SaveLocalFileCrashCustomHandleListenerImp())
        //
        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}