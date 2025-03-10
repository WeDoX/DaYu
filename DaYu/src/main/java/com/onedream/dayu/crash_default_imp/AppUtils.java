package com.onedream.dayu.crash_default_imp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class AppUtils {

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    //应用名称
    public static String getAppName(Context context) {
        PackageInfo pi = getPackageInfo(context);
        if (pi != null) {
            return context.getString(pi.applicationInfo.labelRes);
        } else {
            return "";
        }
    }

    //包信息
    private static PackageInfo getPackageInfo(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
