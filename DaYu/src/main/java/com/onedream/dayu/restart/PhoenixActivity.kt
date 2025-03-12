package com.onedream.dayu.restart

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.PackageManager.FEATURE_LEANBACK
import android.os.Build
import android.os.Bundle
import android.os.Process
import androidx.appcompat.app.AppCompatActivity


/**
 * 先启动子进程，杀死完原先的主进程后，启动新的主进程，然后杀死子进程，达到重启的目的
 * @author chenguijian
 * @since 2025/03/12
 */
class PhoenixActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Kill original main process
        Process.killProcess(intent.getIntExtra(KEY_MAIN_PROCESS_PID, -1))
        //
        val intent = getRestartIntent(this).setFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        //
        finish()
        //
        Runtime.getRuntime().exit(0) // Kill kill kill!
    }

    private fun getRestartIntent(context: Context): Intent {
        val packageName = context.packageName
        var defaultIntent: Intent? = null
        val packageManager = context.packageManager
        if (Build.VERSION.SDK_INT >= 21 && packageManager.hasSystemFeature(FEATURE_LEANBACK)) {
            // Use leanback intent if available, for Android TV apps.
            defaultIntent = packageManager.getLeanbackLaunchIntentForPackage(packageName)
        }
        if (defaultIntent == null) {
            defaultIntent = packageManager.getLaunchIntentForPackage(packageName)
        }
        if (defaultIntent != null) {
            return defaultIntent
        }
        throw IllegalStateException(
            "Unable to determine default activity for "
                    + packageName
                    + ". Does an activity specify the DEFAULT category in its intent filter?"
        )
    }


    companion object {
        const val KEY_MAIN_PROCESS_PID = "KEY_MAIN_PROCESS_PID"

        @JvmStatic
        fun actionStart(context: Context, pid: Int) {
            val intent = Intent(context, PhoenixActivity::class.java)
                .setFlags(FLAG_ACTIVITY_NEW_TASK)
                .putExtra(KEY_MAIN_PROCESS_PID, pid)
            context.startActivity(intent)
        }
    }
}