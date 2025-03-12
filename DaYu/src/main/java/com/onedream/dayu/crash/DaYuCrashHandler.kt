package com.onedream.dayu.crash

import android.content.Context
import android.os.Looper
import android.os.Process
import android.widget.Toast
import com.onedream.dayu.crash.model.CrashLogUploadModelBuilder
import com.onedream.dayu.restart.DaYuPhoenixActivity


object DaYuCrashHandler {

    fun init(context: Context) {
        //
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            handleUncaughtException(context, thread, throwable)
        }
    }

    private fun handleUncaughtException(context: Context, thread: Thread?, throwable: Throwable?) {
        try {
            throwable?.apply {
                val crashLogStr = CrashLogUploadModelBuilder.createCrashLogStr(context, this)
                DaYuCrashLogFileManager.saveToLocalFile(context, crashLogStr)
            }
        } finally {
            restartApp(context)
        }
    }

    private fun restartApp(context: Context) {
        Thread {
            Looper.prepare()
            Toast.makeText(context, "App崩溃,即将重启", Toast.LENGTH_SHORT).show()
            Looper.loop()
        }.start()
        //
        DaYuPhoenixActivity.actionStart(context, Process.myPid())
    }
}