package com.onedream.dayu.protector.whitelist

/**
 *@author chenguijian
 *@since 2025/3/12
 */
object DaYuProtectorWhiteList {

    private var mWhiteListData = ArrayList<DaYuProtectorWhiteListModel>()

    internal fun protect(exception: Exception): Boolean {
        val exceptionClassName = exception.javaClass.simpleName
        for(whiteList in readWhiteList()){
            if(exceptionClassName.equals(whiteList.exception_class_name)){
                return true
            }
        }
        return false
    }


    private fun readWhiteList(): List<DaYuProtectorWhiteListModel> {
        val whiteListData = readWhiteListByLocalFile();
        if(whiteListData.isNullOrEmpty()){
            return defaultWhiteList()
        }
       return whiteListData
    }

    private fun readWhiteListByLocalFile(): List<DaYuProtectorWhiteListModel> {
        //FIXME : 修改为从文件读
        return  mWhiteListData
    }

    private fun defaultWhiteList(): List<DaYuProtectorWhiteListModel> {
        return arrayListOf(DaYuProtectorWhiteListModel("NullPointerException"))
    }

    internal fun writeWhiteList(whiteListData: List<DaYuProtectorWhiteListModel>) {
        //FIXME : 修改为写到文件
        mWhiteListData.clear()
        mWhiteListData.addAll(whiteListData)
    }
}