package com.onedream.dayu.protector

import android.app.Application

/**
 * 老爷保号
 */
object LaoYe {
    private val protectorList = arrayListOf<IProtector>()
    init {
        protectorList.add(MainLooperProtector())
        protectorList.add(TestAPMProtector())
    }

    fun protectApp(app: Application) {
        protectorList.forEach {
            it.protect(app)
        }
    }
}

