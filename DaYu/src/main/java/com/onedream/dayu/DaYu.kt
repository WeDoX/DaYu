package com.onedream.dayu

import android.app.Application
import android.content.Context
import com.onedream.dayu.crash.DaYuCrashHandler
import com.onedream.dayu.crash.DaYuCrashLogFileManager
import com.onedream.dayu.protector.LaoYe
import com.onedream.dayu.protector.whitelist.DaYuProtectorWhiteList
import com.onedream.dayu.protector.whitelist.DaYuProtectorWhiteListModel
import java.io.File

/**
 *@author chenguijian
 *@since 2025/3/12
 */
object DaYu {


    internal fun init(context: Context){
        //
        LaoYe.protectApp((context as Application))
        //
        DaYuCrashHandler.init(context)
    }

    @JvmStatic
    fun getCrashLogFile(context: Context): File? {
        return DaYuCrashLogFileManager.getCrashLogFile(context)
    }

    @JvmStatic
    fun writeWhiteList(whiteListData: List<DaYuProtectorWhiteListModel>) {
        DaYuProtectorWhiteList.writeWhiteList(whiteListData)
    }
}