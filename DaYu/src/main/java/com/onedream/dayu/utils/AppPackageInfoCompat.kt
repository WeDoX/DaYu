package com.onedream.dayu.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi

/**
 *@author chenguijian
 *@since 2025/3/12
 */
object AppPackageInfoCompat {

    @JvmStatic
    fun getVersionName(context: Context): String {
        return getPackageInfo(context).versionName
    }


    @JvmStatic
    fun getVersionCode(context: Context): Long {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return Api28Impl.getVersionCode(getPackageInfo(context))
        }
        return ApiDefaultImpl.getVersionCode(getPackageInfo(context))
    }

    @JvmStatic
    fun getPackageInfo(context: Context): PackageInfo {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return Api33Impl.getPackageInfo(context)
        }
        return ApiDefaultImpl.getPackageInfo(context)
    }
}


internal object ApiDefaultImpl {
    fun getPackageInfo(context: Context): PackageInfo {
        return context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.GET_ACTIVITIES
        )
    }

    fun getVersionCode(packageInfo: PackageInfo): Long {
        return packageInfo.versionCode.toLong()
    }
}


@RequiresApi(Build.VERSION_CODES.P)
internal object Api28Impl {

    fun getVersionCode(packageInfo: PackageInfo): Long {
        return packageInfo.longVersionCode
    }
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
internal object Api33Impl {

    fun getPackageInfo(context: Context): PackageInfo {
        return context.packageManager.getPackageInfo(
            context.packageName,
            PackageManager.PackageInfoFlags.of(PackageManager.GET_ACTIVITIES.toLong())
        )
    }
}


