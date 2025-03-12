package com.onedream.dayu.crash

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

/**
 *@author chenguijian
 *@since 2025/3/12
 */
object DaYuCrashLogFileManager {
    private const val TAG = "DaYuCrashLogFileManager"
    private const val CRASH_LOG_DIR = "Log"
    private const val CRASH_LOG_FILE_NAME = "crash.json"

    //
    private val FileLock = Any()

    @JvmStatic
    internal fun getCrashLogFile(mContext: Context): File? {
        return getFile(mContext, CRASH_LOG_DIR, CRASH_LOG_FILE_NAME)
    }

    @JvmStatic
    internal fun saveToLocalFile(context: Context, crashLogStr: String) {
        printLog("需要存储的崩溃信息为:$crashLogStr")
        writeToFile(
            context,
            CRASH_LOG_DIR,
            CRASH_LOG_FILE_NAME,
            crashLogStr.toByteArray(StandardCharsets.UTF_8)
        )
    }


    private fun writeToFile(
        context: Context,
        dir: String,
        fileName: String,
        data: ByteArray
    ): String? {
        //FIXME
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            printLog("Environment.getExternalStorageState() not equal Environment.MEDIA_MOUNTED")
            return null
        }
        val dirPath = context.getExternalFilesDir(dir)!!.path + File.separator
        val filePath = dirPath + fileName
        try {
            val dirFile = File(dirPath)
            if (!dirFile.exists() && !dirFile.mkdirs()) {
                printLog("fail to mk dir:$dirPath")
                return null
            }
            synchronized(FileLock) {
                val fos = FileOutputStream(filePath, false)
                fos.write(data)
                fos.close()
            }
            return filePath
        } catch (e: Exception) {
            printLog("fail to write:$filePath Exception=$e")
        }
        return null
    }

    private fun getFile(
        context: Context,
        dir: String?,
        fileName: String
    ): File? {
        //FIXME
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            printLog("Environment.getExternalStorageState() not equal Environment.MEDIA_MOUNTED")
            return null
        }
        val dirPath = context.getExternalFilesDir(dir)!!.path + File.separator
        val filePath = dirPath + fileName
        try {
            val dirFile = File(dirPath)
            if (!dirFile.exists() && !dirFile.mkdirs()) {
                printLog("fail to mk dir:$dirPath")
                return null
            }
            val logFile = File(filePath)
            return if (!logFile.exists() || logFile.length() <= 0) {
                null
            } else logFile
        } catch (e: Exception) {
            printLog("fail to get:$filePath Exception=$e")
        }
        return null
    }

    private fun printLog(logContent: String) {
        Log.e(TAG, logContent)
    }
}