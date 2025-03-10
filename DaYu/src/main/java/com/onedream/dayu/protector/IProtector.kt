package com.onedream.dayu.protector

import android.app.Application

interface IProtector {
    fun protect(application: Application)
}