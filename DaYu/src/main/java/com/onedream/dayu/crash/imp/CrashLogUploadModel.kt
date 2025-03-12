package com.onedream.dayu.crash.imp

/**
 * {    "app_info": {        "version_code": "3", // 版本号        "version_name": "1.2.4", //版本名    },    "platform_info": {        "platform": 0, // 0、ios 1、安卓         "version_code": "10.0.7", //版本号        "version_name": "25", // 版本名        "manufacturer": "华为", //制造商        "brand": "荣耀", //品牌        "model": "", // 型号        "arch": "" // cpu架构    },    "crash_info": {        "cash_time": "@timestamp", // 崩溃时间 时间戳 单位毫秒级        "cash_log": "" // 详细崩溃日志    }}
 */
data class CrashLogUploadModel(
    val app_info: AppInfoModel,
    val platform_info: PlatformInfoModel,
    val crash_info: CrashInfoModel
)

data class AppInfoModel(
    val version_code: String,
    val version_name: String
)

data class PlatformInfoModel(
    val version_code: String,
    val version_name: String,
    val manufacturer: String,
    val brand: String,
    val model: String,
    val arch: String,
)

data class CrashInfoModel(
    val crash_time: Long,
    val crash_log: String
)