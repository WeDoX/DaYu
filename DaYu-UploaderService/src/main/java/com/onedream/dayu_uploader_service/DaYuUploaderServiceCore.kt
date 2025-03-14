package com.onedream.dayu_uploader_service

import android.content.Context
import com.onedream.dayu_uploader_service.config.DaYuUploaderServiceConfig
import com.onedream.dayu_uploader_service.utils.DaYuUploadCrashLogQueue
import com.onedream.onenet.OneNet
import com.onedream.onenet.config.OneNetCommonConfig
import java.io.File

/**
 *@author chenguijian
 *@since 2025/3/14
 */
object DaYuUploaderServiceCore {

    private lateinit var mConfig: DaYuUploaderServiceConfig

    internal fun init(context: Context, isDebug: Boolean, config: DaYuUploaderServiceConfig) {
        mConfig = config
        initOneNet(context, isDebug)
    }


    internal fun requireConfig(): DaYuUploaderServiceConfig {
        if (!::mConfig.isInitialized) {
            throw Exception("DaYuUploaderService don't init")
        }
        return mConfig
    }

    private fun initOneNet(context: Context, isDebug: Boolean) {
        OneNet.init(object : OneNetCommonConfig(context, isDebug) {
            override fun baseUrl(): String {
                return requireConfig().baseUrl()
            }

            override fun httpBaseHeader(): Map<String, String>? {
                return requireConfig().httpBaseHeader()
            }
        })
    }

    internal fun uploadLogFile(logFile: File?, success: () -> Unit, error: (String) -> Unit) {
        DaYuUploadCrashLogQueue.uploadLogFile( requireConfig().uploadCrashLogFileShortUrl(), logFile, success, error)
    }
}