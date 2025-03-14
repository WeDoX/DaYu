package com.onedream.dayu

import android.app.Application
import com.onedream.dayu.config.AppDaYuUploaderServiceConfig
import com.onedream.dayu_uploader_service.DaYuUploaderService

/**
 *@author chenguijian
 *@since 2025/3/14
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //
        DaYuUploaderService.init(this.applicationContext, true, AppDaYuUploaderServiceConfig())
    }
}