package com.onedream.dayu_uploader_service.utils;

import java.net.FileNameMap;
import java.net.URLConnection;

import okhttp3.MediaType;

public class FileTypeGuessHelper {
    //根据文件路径猜测出文件类型
    public static MediaType guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        path = path.replace("#", "");   //解决文件名中含有#号异常的问题
        String contentType = fileNameMap.getContentTypeFor(path);
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return MediaType.parse(contentType);
    }
}
