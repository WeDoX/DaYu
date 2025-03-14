package com.onedream.dayu_uploader_service.http

import com.onedream.onenet.model.BaseResp
import com.onedream.onenet.utils.RetrofitFactory
import okhttp3.RequestBody

/**
 *@author chenguijian
 *@since 2025/3/14
 */
object HttpDataRepository {

    private val apiService: HttpApiService by lazy {
        RetrofitFactory.create(
            HttpApiService::class.java
        )
    }

    suspend fun crashLogUpload(shortUrlPath :String, requestBody: RequestBody): BaseResp<Any> {
        return apiService.crashLogUpload(shortUrlPath, requestBody)
    }
}