package com.li.mykotlinapp.common

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

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
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        lateinit var instance: App
    }
}