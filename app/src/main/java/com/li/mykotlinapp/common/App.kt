package com.li.mykotlinapp.common

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.li.mykotlinapp.biz.db.ObjectBox
import com.li.mykotlinapp.util.CrashHandler
import com.tencent.bugly.Bugly

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.common
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/8
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class App : Application() {
    init {
        instance = this
    }
    override fun onCreate() {
        super.onCreate()
//        CrashHandler.getInstance().init(this)
        ObjectBox.init(this)

        Bugly.init(applicationContext, "d0cd42083f", true);

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        lateinit var instance: App
    }
}