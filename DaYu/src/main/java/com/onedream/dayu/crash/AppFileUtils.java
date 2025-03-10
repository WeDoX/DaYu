package com.onedream.dayu.crash;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;

public class AppFileUtils {
    private static final String TAG = "=======AppFileUtils";
    public static final Object FileLock = new Object();

    public static String WriteToFile(Context context,
                                     String dir,
                                     String fileName,
                                     byte[] data) {

        String path = context.getExternalFilesDir(dir).getPath() + "/";

        try {
            File dirStr = new File(path);
            if (!dirStr.exists() && !dirStr.mkdirs()) {
                printLog(TAG, "fail to mk dir:" + path);
                return null;
            }
            synchronized (FileLock) {
                FileOutputStream fos = new FileOutputStream(path + fileName, false);
                fos.write(data);
                fos.close();
            }
            return path + fileName;
        } catch (Exception e) {
            printLog(TAG, "fail to write:" + path + "/" + fileName + " Exception=" + e.toString());
        }
        return null;
    }


    public static File getFile(Context context,
                               String dir,
                               String fileName) {
        String path = context.getExternalFilesDir(dir).getPath() + "/";
        try {
            File dirStr = new File(path);
            if (!dirStr.exists() && !dirStr.mkdirs()) {
                printLog(TAG, "fail to mk dir:" + path);
                return null;
            }
            File logFile = new File(path + fileName);
            if (!logFile.exists() || logFile.length() <= 0) {
                return null;
            }
            return logFile;
        } catch (Exception e) {
            printLog(TAG, "fail to write:" + path + "/" + fileName + " Exception=" + e.toString());
        }
        return null;
    }

    private static void printLog(@NonNull String tag, @NonNull String message) {
        Log.e(tag, message);
    }

}