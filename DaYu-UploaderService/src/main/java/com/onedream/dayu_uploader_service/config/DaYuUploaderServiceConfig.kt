package com.onedream.dayu_uploader_service.config

/**
 *@author chenguijian
 *@since 2025/3/14
 */
interface DaYuUploaderServiceConfig {
    fun baseUrl(): String

    fun httpBaseHeader(): Map<String, String>?

    fun uploadCrashLogFileShortUrl():String
}