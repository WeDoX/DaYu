package com.onedream.dayu.crash;

import android.content.Context;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.onedream.dayu.restart.PhoenixActivity;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * <p>
 * 需要在Application中注册，为了要在程序启动器就监控整个程序。
 */
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    //系统默认的UncaughtException处理类       
    private UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例      
    private volatile static CrashHandler instance;
    //程序的Context对象
    private Context mContext;
    //
    private CrashCustomHandleListener mCrashCustomHandleListener;

    public interface CrashCustomHandleListener {
        void handleException(Context mContext, Throwable ex);
    }


    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {

    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context, CrashCustomHandleListener crashCustomHandleListener) {
        mContext = context;
        //获取系统默认的UncaughtException处理器      
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器      
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.mCrashCustomHandleListener = crashCustomHandleListener;
    }

    @Override
    public void uncaughtException(@NonNull Thread thread, Throwable ex) {
        printLog("ATU奔溃异常" + TAG + ex.toString());
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理      
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            printLog("ATU没处理的奔溃异常" + TAG + ex.toString());
            //使用Toast来显示异常信息
            new Thread() {
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(mContext, "App崩溃,即将重启", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();
            //
            PhoenixActivity.actionStart(mContext, Process.myPid());
        }
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        if (null != mCrashCustomHandleListener) {
            mCrashCustomHandleListener.handleException(mContext, ex);
        }
        return true;
    }

    private void printLog(String logContent) {
        Log.e("ATU_" + TAG, logContent);
    }
}