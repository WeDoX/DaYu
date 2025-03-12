package com.onedream.dayu.crash.model

import android.content.Context
import android.os.Build
import com.google.gson.Gson
import com.onedream.dayu.utils.AppPackageInfoCompat
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.util.Arrays

object CrashLogUploadModelBuilder {

    @JvmStatic
    fun createCrashLogStr(
        context: Context,
        throwable: Throwable
    ): String {
        val crashLogUpload = createCrashLogUpload(context, throwable)
        return Gson().toJson(crashLogUpload)
    }

    private fun createCrashLogUpload(
        context: Context,
        throwable: Throwable
    ): CrashLogUploadModel {

        val appInfo = createAppInfo(context)
        val platformInfo = createPlatformInfo()
        val crashInfo = createCrashInfo(throwable)

        return CrashLogUploadModel(
            app_info = appInfo,
            platform_info = platformInfo,
            crash_info = crashInfo
        )

    }

    private fun createAppInfo(context: Context): AppInfoModel {
        return AppInfoModel(
            version_code = AppPackageInfoCompat.getVersionCode(context).toString(),
            version_name = AppPackageInfoCompat.getVersionName(context)
        )
    }

    private fun createPlatformInfo(): PlatformInfoModel {

        val arch = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Arrays.toString(Build.SUPPORTED_ABIS)
        } else {
            arrayOf(Build.CPU_ABI, Build.CPU_ABI2).toString()
        }

        return PlatformInfoModel(
            version_code = Build.VERSION.SDK_INT.toString(),
            version_name = Build.VERSION.RELEASE,
            manufacturer = Build.MANUFACTURER,
            brand = Build.BRAND,
            model = Build.MODEL,
            arch = arch
        )
    }


    private fun createCrashInfo(throwable: Throwable): CrashInfoModel {
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        throwable.printStackTrace(printWriter)
        var cause = throwable.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val crashLog = writer.toString()
        //
        return CrashInfoModel(System.currentTimeMillis(), crashLog)
    }
}