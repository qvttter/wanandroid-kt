package com.li.mykotlinapp.biz.db

import android.content.Context
import com.li.mykotlinapp.bean.db.MyObjectBox
import io.objectbox.BoxStore

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.biz.db
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/7/8
 *@Copyright:(C)2020 . All rights reserved.
 *************************************************************************/
object ObjectBox {
    lateinit var boxStore: BoxStore
        private set

    fun init(context: Context) {
        boxStore = MyObjectBox.builder().androidContext(context.applicationContext).build()

//        if (BuildConfig.DEBUG) {
//            Log.d(App.TAG, "Using ObjectBox ${BoxStore.getVersion()} (${BoxStore.getVersionNative()})")
//            AndroidObjectBrowser(boxStore).start(context.applicationContext)
//        }
    }
}