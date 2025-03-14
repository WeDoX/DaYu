package com.onedream.dayu_uploader_service.http

import com.onedream.onenet.model.BaseResp
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

/**
 *@author chenguijian
 *@since 2025/3/14
 */
interface HttpApiService {
    @POST("{shortUrlPath}")
    suspend fun crashLogUpload(@Path("shortUrlPath") shortUrlPath :String, @Body requestBody: RequestBody): BaseResp<Any>
}