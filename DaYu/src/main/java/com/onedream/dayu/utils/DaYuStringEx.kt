package com.onedream.dayu.utils

/**
 *@author chenguijian
 *@since 2025/3/12
 */

fun String?.notNull(): String {
    if (null == this) {
        return ""
    }
    return this
}