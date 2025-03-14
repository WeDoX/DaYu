package com.onedream.dayu_uploader_service.utils


import android.util.Log
import com.google.gson.Gson
import com.onedream.dayu_uploader_service.http.HttpDataRepository
import com.onedream.onenet.model.BaseResp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

object DaYuUploadCrashLogQueue {

    private var isLogUploadFinish: Boolean = true

    internal fun uploadLogFile(shortUrlPath :String, logFile: File?, success: () -> Unit, error: (String) -> Unit) {
        if (!isLogUploadFinish) {
            logErr("上传崩溃日志任务正在执行中")
            error.invoke("上传崩溃日志任务正在执行中")
            return
        }
        isLogUploadFinish = false
        logErr("上传崩溃日志任务：开始上传")
        if (null == logFile) {
            logErr("上传崩溃日志任务：没有需要上传的信息")
            error.invoke("上传崩溃日志任务：没有需要上传的信息")
            isLogUploadFinish = true
            return
        }
        Thread {
            try {
                runBlocking(Dispatchers.IO) {
                    val uploadRespAsync = async {
                        val contentType =
                            FileTypeGuessHelper.guessMimeType(logFile.absolutePath)
                        val fileBody: RequestBody = RequestBody.create(contentType, logFile)
                        val requestBody: RequestBody = MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("platform", "1")//1-安卓
                            .addFormDataPart("file", logFile.name, fileBody)
                            .build()
                        HttpDataRepository.crashLogUpload(shortUrlPath, requestBody)
                    }
                    val uploadResp: BaseResp<Any> = uploadRespAsync.await()
                    logErr("上传崩溃日志任务：上传结果" + Gson().toJson(uploadResp))
                    val dataAndMsg = uploadResp.apiDataAndMsg()
                    logErr("上传崩溃日志任务：上传结果成功")
                    try {
                        logFile.delete()
                        logErr("上传崩溃日志任务：上传结果成功，删除文件成功")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        logErr("上传崩溃日志任务：上传结果成功，删除文件失败，异常：${e}")
                    }
                    success.invoke()
                }
            } catch (e: Exception) {
                logErr("上传崩溃日志出现异常：$e")
                error.invoke("上传崩溃日志出现异常：$e")
            } finally {
                isLogUploadFinish = true
            }
        }.start()

    }

    private fun logErr(message: String) {
        Log.e("DaYuUploaderService", message)
    }
}