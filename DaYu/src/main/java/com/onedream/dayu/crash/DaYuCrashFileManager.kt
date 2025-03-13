package com.onedream.dayu.crash

import android.content.Context
import android.util.Log
import com.onedream.dayu.utils.DaYuFileManager
import java.io.File
import java.nio.charset.StandardCharsets

/**
 *@author chenguijian
 *@since 2025/3/12
 */
object DaYuCrashFileManager {
    private const val TAG = "DaYuCrashLogFileManager"
    private const val CRASH_LOG_DIR = "DaYu_log"
    private const val CRASH_LOG_FILE_NAME = "crash.json"

    //
    private val FILE_LOCK = Any()

    @JvmStatic
    internal fun getCrashLogFile(mContext: Context): File? {
        return DaYuFileManager.getFile(mContext, CRASH_LOG_DIR, CRASH_LOG_FILE_NAME)
    }

    @JvmStatic
    internal fun saveToLocalFile(context: Context, crashLogStr: String) {
        printLog("需要存储的崩溃信息为:$crashLogStr")
        DaYuFileManager.writeToFile(
            context,
            CRASH_LOG_DIR,
            CRASH_LOG_FILE_NAME,
            crashLogStr.toByteArray(StandardCharsets.UTF_8),
            FILE_LOCK
        )
    }

    private fun printLog(logContent: String) {
        Log.e(TAG, logContent)
    }
}