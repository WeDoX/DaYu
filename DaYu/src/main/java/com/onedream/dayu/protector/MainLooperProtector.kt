package com.onedream.dayu.protector

import android.app.Application
import android.os.Handler
import android.os.Looper
import com.onedream.dayu.protector.whitelist.DaYuExceptionWhiteListManager

class MainLooperProtector : IProtector {
    override fun protect(application: Application) {
        protectMainLooper()
    }

    /**
     * 核心代码其实就是Looper.loop()，android系统是基于事件驱动运行起来的，本质上来说相当于一个死循环，
     * 典型的生产消费者模型，当没有事件的时候会进行阻塞，有事件过来会交给Handler来进行处理
     * 当发生crash时，loop()发生异常，会退出死循环的。
     * 所以我做的就是捕获异常，并重新让它转起来！我为了对异常保护流程进行控制，所以使用的是递归来让它重新运行。
     *
     * 涂鸦SDK获取室内种植设备的智能场景动作列表时回调时出现空指针，简短代码如下：
     * Handler(Looper.getMainLooper()).post {
     *      throw NullPointerException()
     * }
     */
    private fun protectMainLooper() {
        Handler(Looper.getMainLooper()).post {
            try {
                Looper.loop()
            } catch (exception: Exception) {
                handleProcessException(exception)
            }
        }
    }

    private fun handleProcessException(exception: Exception) {
        if (DaYuExceptionWhiteListManager.protect(exception)) {
            protectMainLooper()
            return
        }
        throw exception
    }
}