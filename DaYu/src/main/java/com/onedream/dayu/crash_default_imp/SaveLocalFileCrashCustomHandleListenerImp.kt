package com.onedream.dayu.crash_default_imp

import android.content.Context
import android.os.Environment
import android.util.Log
import com.onedream.dayu.utils.AppFileUtils
import com.onedream.dayu.crash.CrashHandler.CrashCustomHandleListener
import java.io.File
import java.nio.charset.StandardCharsets

class SaveLocalFileCrashCustomHandleListenerImp : CrashCustomHandleListener {

    override fun handleException(context: Context, throwable: Throwable) {
        val crashLogUpload = CrashLogUploadModelBuilder.createCrashLogUpload(context, throwable)
        //todo 序列化
        //String crashLogUploadModelJsonStr = StringHelper.notNull(JacksonUtils.toJson(crashLogUploadModel));
        val crashLogUploadModelJsonStr = ""
        //
        Log.e("ATU", "崩溃信息为:$crashLogUploadModelJsonStr")
        //

    }

    private fun saveToLocalFile(context: Context, crashLogStr: String){
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
        Log.e("ATU_" + TAG, logContent)
    }

    companion object {
        const val TAG = "CrashHandler"
        private const val CRASH_LOG_DIR = "Log"
        private const val CRASH_LOG_FILE_NAME = "crash.json"

        //
        fun getCrashLogFile(mContext: Context): File {
            return AppFileUtils.getFile(mContext, CRASH_LOG_DIR, CRASH_LOG_FILE_NAME)
        }
    }
}