package com.onedream.dayu.crash_default_imp;

/**
 * {    "app_info": {        "version_code": "3", // 版本号        "version_name": "1.2.4", //版本名    },    "platform_info": {        "platform": 0, // 0、ios 1、安卓         "version_code": "10.0.7", //版本号        "version_name": "25", // 版本名        "manufacturer": "华为", //制造商        "brand": "荣耀", //品牌        "model": "", // 型号        "arch": "" // cpu架构    },    "crash_info": {        "cash_time": "@timestamp", // 崩溃时间 时间戳 单位毫秒级        "cash_log": "" // 详细崩溃日志    }}
 */
public class CrashLogUploadModel{
    private String user_id;
    private AppInfoModel app_info;
    private PlatformInfoModel platform_info;
    private CrashInfoModel crash_info;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public AppInfoModel getApp_info() {
        return app_info;
    }

    public void setApp_info(AppInfoModel app_info) {
        this.app_info = app_info;
    }

    public PlatformInfoModel getPlatform_info() {
        return platform_info;
    }

    public void setPlatform_info(PlatformInfoModel platform_info) {
        this.platform_info = platform_info;
    }

    public CrashInfoModel getCrash_info() {
        return crash_info;
    }

    public void setCrash_info(CrashInfoModel crash_info) {
        this.crash_info = crash_info;
    }

    public static class AppInfoModel{
        private String version_code;
        private String version_name;

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }
    }

    //"platform_info": {       "version_code": "10.0.7", //版本号        "version_name": "25", // 版本名        "manufacturer": "华为", //制造商        "brand": "荣耀", //品牌        "model": "", // 型号        "arch": "" // cpu架构    }
    public static class PlatformInfoModel{
        private String version_code;
        private String version_name;
        private String manufacturer;
        private String brand;
        private String model;
        private String arch;

        public String getVersion_code() {
            return version_code;
        }

        public void setVersion_code(String version_code) {
            this.version_code = version_code;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getArch() {
            return arch;
        }

        public void setArch(String arch) {
            this.arch = arch;
        }
    }

    //"crash_info": {        "crash_time": "@timestamp", // 崩溃时间 时间戳 单位毫秒级        "crash_log": "" // 详细崩溃日志    }
    public static class CrashInfoModel{
        private long crash_time;
        private String crash_log;

        public long getCrash_time() {
            return crash_time;
        }

        public void setCrash_time(long crash_time) {
            this.crash_time = crash_time;
        }

        public String getCrash_log() {
            return crash_log;
        }

        public void setCrash_log(String crash_log) {
            this.crash_log = crash_log;
        }
    }


}
