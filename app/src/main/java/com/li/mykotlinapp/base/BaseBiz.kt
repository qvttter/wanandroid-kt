package com.li.mykotlinapp.base

import android.content.Context
import com.li.mykotlinapp.common.App
import com.li.mykotlinapp.common.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
open class BaseBiz {
    var retrofit: Retrofit
    var mContext: Context
    var okHttpClient: OkHttpClient

    constructor() {
        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .build()
        okHttpClient.dispatcher().maxRequestsPerHost = 10
        mContext = App.instance
        retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

    protected fun <T> createService(clazz: Class<T>): T {
        return this.retrofit.create(clazz)
    }
}