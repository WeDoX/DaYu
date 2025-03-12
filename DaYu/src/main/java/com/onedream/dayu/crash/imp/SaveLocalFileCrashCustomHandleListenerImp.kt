package com.onedream.dayu.crash.imp

import android.content.Context
import com.onedream.dayu.crash.CrashHandler.CrashCustomHandleListener

class SaveLocalFileCrashCustomHandleListenerImp : CrashCustomHandleListener {

    override fun handleException(context: Context, throwable: Throwable) {
        val crashLogStr = CrashLogUploadModelBuilder.createCrashLogStr(context, throwable)
        //
        DaYuCrashLogFileManager.saveToLocalFile(context, crashLogStr)
    }
}