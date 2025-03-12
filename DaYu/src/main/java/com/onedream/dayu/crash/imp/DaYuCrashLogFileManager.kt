package com.onedream.dayu.crash.imp

import android.content.Context
import android.os.Environment
import android.util.Log
import com.onedream.dayu.utils.AppFileUtils
import java.io.File
import java.nio.charset.StandardCharsets

/**
 *@author chenguijian
 *@since 2025/3/12
 */
object DaYuCrashLogFileManager {
    private const val TAG = "DaYuCrashLogFileManager"
    private const val CRASH_LOG_DIR = "Log"
    private const val CRASH_LOG_FILE_NAME = "crash.json"

    @JvmStatic
    fun getCrashLogFile(mContext: Context): File {
        return AppFileUtils.getFile(mContext, CRASH_LOG_DIR, CRASH_LOG_FILE_NAME)
    }

    @JvmStatic
    fun saveToLocalFile(context: Context, crashLogStr: String){
        printLog("崩溃信息为:$crashLogStr")
        try {
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val path = AppFileUtils.WriteToFile(
                    context,
                    CRASH_LOG_DIR,
                    CRASH_LOG_FILE_NAME,
                    crashLogStr.toByteArray(StandardCharsets.UTF_8)
                )
            }
        } catch (e: Exception) {
            printLog("an loadsir_error occured while writing file...$e")
        }
    }

    private fun printLog(logContent: String) {
        Log.e("ATU_", logContent)
    }
}