package com.onedream.dayu.protector

import android.app.Application
import android.os.Looper
import android.util.Log

/**
 *@author chenguijian
 *@since 2025/3/13
 */
class TestAPMProtector : IProtector {

    override fun protect(application: Application) {
        var startWorkTimeMillis = 0L
        Looper.getMainLooper().setMessageLogging {
            if (it.startsWith(">>>>> Dispatching to Handler")) {
                startWorkTimeMillis = System.currentTimeMillis()
            } else if (it.startsWith("<<<<< Finished to Handler")) {
                val duration = System.currentTimeMillis() - startWorkTimeMillis
                if (duration > 1000) {
                    printLog("主线程执行耗时过长: $duration 毫秒，$it")
                }
            }
        }
    }

    private fun printLog(logContent: String) {
        Log.e("ANRProtector", logContent)
    }
}