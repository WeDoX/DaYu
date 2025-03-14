package com.onedream.dayu.config

import com.onedream.dayu_uploader_service.config.DaYuUploaderServiceConfig

/**
 *@author chenguijian
 *@since 2025/3/14
 */
class AppDaYuUploaderServiceConfig : DaYuUploaderServiceConfig {
    override fun baseUrl(): String {
        return AppHttpConfig.BASE_URL
    }

    override fun httpBaseHeader(): Map<String, String>? {
        return null //http头部鉴权之类的
    }

    override fun uploadCrashLogFileShortUrl(): String {
        return AppHttpConfig.UPLOAD_CRASH_LOG_SHORT_URL
    }
}