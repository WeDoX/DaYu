package com.onedream.dayu.crash_default_imp;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.onedream.dayu.crash.AppFileUtils;
import com.onedream.dayu.crash.CrashHandler;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class SaveLocalFileCrashCustomHandleListenerImp implements CrashHandler.CrashCustomHandleListener {

    public static final String TAG = "CrashHandler";
    private static final String CRASH_LOG_DIR = "Log";
    private static final String CRASH_LOG_FILE_NAME = "crash.json";

    @Override
    public void handleException(Context mContext, Throwable ex) {
        CrashLogUploadModel crashLogUploadModel = new CrashLogUploadModel();
        //todo 用户ID
        crashLogUploadModel.setUser_id("用户ID");
        //1、
        CrashLogUploadModel.AppInfoModel appInfoModel = new CrashLogUploadModel.AppInfoModel();
        appInfoModel.setVersion_code(AppUtils.getVersionCode(mContext) + "");
        appInfoModel.setVersion_name(AppUtils.getVersionName(mContext));
        crashLogUploadModel.setApp_info(appInfoModel);
        //2、
        crashLogUploadModel.setPlatform_info(CrashLogUploadModelConvert.generator());
        //3、
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String crashLog = writer.toString();
        //
        CrashLogUploadModel.CrashInfoModel crashInfoModel = new CrashLogUploadModel.CrashInfoModel();
        crashInfoModel.setCrash_time(System.currentTimeMillis());
        crashInfoModel.setCrash_log(crashLog);
        //
        crashLogUploadModel.setCrash_info(crashInfoModel);
        //todo 序列化
        //String crashLogUploadModelJsonStr = StringHelper.notNull(JacksonUtils.toJson(crashLogUploadModel));
        String crashLogUploadModelJsonStr = "";
        //
        Log.e("ATU", "崩溃信息为:" + crashLogUploadModelJsonStr);
        //
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = AppFileUtils.WriteToFile(mContext,
                        CRASH_LOG_DIR,
                        CRASH_LOG_FILE_NAME,
                        crashLogUploadModelJsonStr.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            printLog("an loadsir_error occured while writing file..." + e.toString());
        }
    }

    public static File getCrashLogFile(Context mContext) {
        return AppFileUtils.getFile(mContext, CRASH_LOG_DIR, CRASH_LOG_FILE_NAME);
    }


    private void printLog(String logContent) {
        Log.e("ATU_" + TAG, logContent);
    }
}
