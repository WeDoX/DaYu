package com.onedream.dayu

import android.app.Application
import android.content.Context
import com.onedream.dayu.crash.DaYuCrashHandler
import com.onedream.dayu.crash.DaYuCrashLogFileManager
import com.onedream.dayu.protector.LaoYe
import com.onedream.dayu.protector.whitelist.DaYuExceptionWhiteListManager
import com.onedream.dayu.protector.whitelist.DaYuExceptionWhiteListModel
import java.io.File

/**
 *@author chenguijian
 *@since 2025/3/12
 */
object DaYu {

    private var applicationContext: Context? = null

    internal fun init(context: Context){
        //
        applicationContext = context
        //
        LaoYe.protectApp((context as Application))
        //
        DaYuCrashHandler.init(context)
    }

    fun requireContext(): Context {
        return applicationContext ?: throw IllegalStateException("DaYu don't call init(context) method")
    }

    @JvmStatic
    fun getCrashLogFile(context: Context): File? {
        return DaYuCrashLogFileManager.getCrashLogFile(context)
    }

    @JvmStatic
    fun writeWhiteList(whiteListData: List<DaYuExceptionWhiteListModel>, append : Boolean) {
        DaYuExceptionWhiteListManager.writeWhiteList(whiteListData, append)
    }
}