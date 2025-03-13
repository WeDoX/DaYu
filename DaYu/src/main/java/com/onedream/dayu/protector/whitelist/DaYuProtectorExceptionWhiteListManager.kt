package com.onedream.dayu.protector.whitelist

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.onedream.dayu.DaYu


/**
 *@author chenguijian
 *@since 2025/3/12
 */
object DaYuProtectorExceptionWhiteListManager {

    internal fun protect(exception: Exception): Boolean {
        val exceptionClassName = exception.javaClass.simpleName
        for (whiteList in readWhiteList()) {
            if (exceptionClassName.equals(whiteList.exception_class_name)) {
                return true
            }
        }
        return false
    }


    private fun readWhiteList(): List<DaYuProtectorExceptionWhiteListModel> {
        val whiteListData = readWhiteListByLocalFile();
        if (whiteListData.isNullOrEmpty()) {
            return defaultWhiteList()
        }
        return whiteListData
    }

    private fun readWhiteListByLocalFile(): List<DaYuProtectorExceptionWhiteListModel>? {
        val fileContent =
            DaYuProtectorExceptionWhiteListFileManager.getExceptionWhiteListFileContent(DaYu.requireContext())
        if (fileContent.isNullOrEmpty()) {
            return null
        }
        try {
            val type: TypeToken<*>? = TypeToken.get(object :
                TypeToken<List<DaYuProtectorExceptionWhiteListModel>>() {}.type)
            return Gson().fromJson(
                fileContent,
                type!!.type
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ArrayList()
    }

    private fun defaultWhiteList(): List<DaYuProtectorExceptionWhiteListModel> {
        return arrayListOf(
            DaYuProtectorExceptionWhiteListModel("NullPointerException")
        )
    }

    internal fun writeWhiteList(
        whiteListData: List<DaYuProtectorExceptionWhiteListModel>,
        append: Boolean
    ) {
        if (append) {
            //FIXME : 考虑去重
            val newWhiteListData = ArrayList<DaYuProtectorExceptionWhiteListModel>()
            newWhiteListData.addAll(readWhiteList())
            newWhiteListData.addAll(whiteListData)
            DaYuProtectorExceptionWhiteListFileManager.saveToLocalFile(
                DaYu.requireContext(),
                Gson().toJson(newWhiteListData)
            )
        } else {
            DaYuProtectorExceptionWhiteListFileManager.saveToLocalFile(
                DaYu.requireContext(),
                Gson().toJson(whiteListData)
            )
        }
    }
}