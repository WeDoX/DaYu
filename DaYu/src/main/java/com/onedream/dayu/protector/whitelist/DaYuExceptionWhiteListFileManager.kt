package com.onedream.dayu.protector.whitelist

import android.content.Context
import android.util.Log
import com.onedream.dayu.utils.DaYuFileManager
import java.nio.charset.StandardCharsets

/**
 *@author chenguijian
 *@since 2025/3/13
 */
object DaYuExceptionWhiteListFileManager {
    private const val TAG = "WhiteListFileManager"
    private const val WHITELIST_DIR = "DaYu_protector"
    private const val WHITELIST_FILE_NAME = "exception_whitelist.json"

    //
    private val FILE_LOCK = Any()

    @JvmStatic
    internal fun getExceptionWhiteListFileContent(mContext: Context): String? {
        val file = DaYuFileManager.getFile(mContext, WHITELIST_DIR, WHITELIST_FILE_NAME)
        return DaYuFileManager.getFileContent(file)
    }


    @JvmStatic
    internal fun saveToLocalFile(context: Context, whitelistStr: String) {
        printLog("需要存储的白名单信息为:$whitelistStr")
        DaYuFileManager.writeToFile(
            context,
            WHITELIST_DIR,
            WHITELIST_FILE_NAME,
            whitelistStr.toByteArray(StandardCharsets.UTF_8),
            FILE_LOCK
        )
    }

    private fun printLog(logContent: String) {
        Log.e(TAG, logContent)
    }
}