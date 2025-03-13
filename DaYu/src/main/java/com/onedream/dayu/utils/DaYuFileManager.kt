package com.onedream.dayu.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets

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
        data: String,
        lock: Any,
    ): String? {
        //
        val dirPath = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            "${context.getExternalFilesDir(dir)!!.path}${File.separator}"
        }else{
            "${context.filesDir.path}${File.separator}${dir}${File.separator}"
        }

        val filePath = dirPath + fileName
        var fileOutputStream: FileOutputStream? = null
        try {
            val dirFile = File(dirPath)
            if (!dirFile.exists() && !dirFile.mkdirs()) {
                printLog("fail to mk dir:$dirPath")
                return null
            }
            synchronized(lock) {
                fileOutputStream = FileOutputStream(filePath, false).apply {
                    write(data.toByteArray(StandardCharsets.UTF_8))
                }
            }
            return filePath
        } catch (e: Exception) {
            printLog("fail to write:$filePath Exception=$e")
        } finally {
            fileOutputStream?.close()
        }
        return null
    }

    @JvmStatic
    fun getFile(
        context: Context,
        dir: String?,
        fileName: String
    ): File? {
        //
        val dirPath = if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            "${context.getExternalFilesDir(dir)!!.path}${File.separator}"
        }else{
            "${context.filesDir.path}${File.separator}${dir}${File.separator}"
        }

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
            fileStr = String(buffer, StandardCharsets.UTF_8)
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