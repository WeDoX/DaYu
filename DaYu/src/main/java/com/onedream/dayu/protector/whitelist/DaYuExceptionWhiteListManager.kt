package com.onedream.dayu.protector.whitelist

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.onedream.dayu.DaYu
import com.onedream.dayu.protector.whitelist.model.DaYuExceptionWhiteListModel


/**
 *@author chenguijian
 *@since 2025/3/12
 */
object DaYuExceptionWhiteListManager {

    internal fun protect(exception: Exception): Boolean {
        val exceptionClassName = exception.javaClass.simpleName
        for (whiteList in readWhiteList()) {
            if (exceptionClassName.equals(whiteList.exception_class_name)) {
                return true
            }
        }
        return false
    }


    private fun readWhiteList(): List<DaYuExceptionWhiteListModel> {
        val whiteListData = readWhiteListByLocalFile();
        if (whiteListData.isNullOrEmpty()) {
            return defaultWhiteList()
        }
        return whiteListData
    }

    private fun readWhiteListByLocalFile(): List<DaYuExceptionWhiteListModel>? {
        val fileContent =
            DaYuExceptionWhiteListFileManager.getExceptionWhiteListFileContent(DaYu.requireContext())
        if (fileContent.isNullOrEmpty()) {
            return null
        }
        try {
            val type: TypeToken<*>? = TypeToken.get(object :
                TypeToken<List<DaYuExceptionWhiteListModel>>() {}.type)
            return Gson().fromJson(
                fileContent,
                type!!.type
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ArrayList()
    }

    private fun defaultWhiteList(): List<DaYuExceptionWhiteListModel> {
        return arrayListOf(
            DaYuExceptionWhiteListModel("NullPointerException")
        )
    }

    internal fun writeWhiteList(
        whiteListData: List<DaYuExceptionWhiteListModel>,
        append: Boolean
    ) {
        if (append) {
            val newWhiteListData = ArrayList<DaYuExceptionWhiteListModel>()
            newWhiteListData.addAll(readWhiteList().union(whiteListData))
            DaYuExceptionWhiteListFileManager.saveToLocalFile(
                DaYu.requireContext(),
                Gson().toJson(newWhiteListData)
            )
        } else {
            DaYuExceptionWhiteListFileManager.saveToLocalFile(
                DaYu.requireContext(),
                Gson().toJson(whiteListData)
            )
        }
    }
}