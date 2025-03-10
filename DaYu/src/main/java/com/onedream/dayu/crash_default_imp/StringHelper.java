package com.onedream.dayu.crash_default_imp;

import android.text.TextUtils;

public class StringHelper {

    //防止setText时对象为空报错
    public static String notNull(String text) {
        return notNull(text, "");
    }

    //如果为空，返回默认值
    public static String notNull(String text, String defaultText) {
        if (!isEmpty(text)) {
            return text;
        }
        return defaultText;
    }

    public static boolean isEmpty(String text) {
        return null == text || TextUtils.isEmpty(text);
    }
}
