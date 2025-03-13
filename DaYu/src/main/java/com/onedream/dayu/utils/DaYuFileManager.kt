package com.onedream.dayu.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 *@author chenguijian
 *@since 2025/3/13
 */
object DaYuFileManager {
    private const val TAG = "DaYuCrashLogFileManager"

    @JvmStatic
    fun writeToFile(
        context: Context,
        dir: String,
        fileName: String,
        data: ByteArray,
        lock: Any,
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
            synchronized(lock) {
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

    @JvmStatic
    fun getFile(
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

    @JvmStatic
    fun getFileContent(file: File?): String? {
        if (null == file) {
            return null
        }
        var fileStr: String? = null
        var inputStream: InputStream? = null
        try {
            inputStream = file.inputStream()
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            fileStr = String(buffer, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            inputStream?.close()
        }
        return fileStr
    }

    private fun printLog(logContent: String) {
        Log.e(TAG, logContent)
    }
}