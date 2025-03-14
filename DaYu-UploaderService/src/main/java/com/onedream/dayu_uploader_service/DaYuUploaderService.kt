package com.onedream.dayu_uploader_service

import android.content.Context
import com.onedream.dayu_uploader_service.config.DaYuUploaderServiceConfig
import java.io.File


/**
 *@author chenguijian
 *@since 2025/3/14
 */
object DaYuUploaderService {

    fun init(context: Context, isDebug: Boolean, config: DaYuUploaderServiceConfig) {
        DaYuUploaderServiceCore.init(context, isDebug, config)
    }

    fun uploadLogFile(logFile: File?, success: () -> Unit, error: (String) -> Unit) {
        DaYuUploaderServiceCore.uploadLogFile(logFile, success, error)
    }
}