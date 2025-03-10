package com.onedream.dayu.crash_default_imp;

import android.os.Build;

import java.util.Arrays;

public class CrashLogUploadModelConvert {

    public static CrashLogUploadModel.PlatformInfoModel generator(){
        CrashLogUploadModel.PlatformInfoModel platformInfoModel = new CrashLogUploadModel.PlatformInfoModel();
        platformInfoModel.setVersion_code(Build.VERSION.SDK_INT+"");
        platformInfoModel.setVersion_name(Build.VERSION.RELEASE);
        platformInfoModel.setManufacturer(Build.MANUFACTURER);
        platformInfoModel.setBrand(Build.BRAND);
        platformInfoModel.setModel(Build.MODEL);
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            platformInfoModel.setArch(Arrays.toString(Build.SUPPORTED_ABIS));
        }else{
            platformInfoModel.setArch("[\""+Build.CPU_ABI+"\",\""+Build.CPU_ABI2+"\"]");
        }
        return platformInfoModel;
    }
}
