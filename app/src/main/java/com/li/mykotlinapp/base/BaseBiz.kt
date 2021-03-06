package com.li.mykotlinapp.base

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.li.mykotlinapp.common.App
import com.li.mykotlinapp.common.Constants
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
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
        mContext = App.instance

        val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(mContext))
        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(20000, TimeUnit.MILLISECONDS)
                .readTimeout(20000, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .build()
        okHttpClient.dispatcher().maxRequestsPerHost = 10
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

    open fun <T> getData(response: BaseListResponse<T>): Observable<List<T>?>? {
        return Observable.create { emitter: ObservableEmitter<List<T>?> ->
            if (response.errorCode == 0) {
                emitter.onNext(response.data)
                emitter.onComplete()
            } else {
                emitter.onError(Throwable(response.errorMsg))
            }
        }
    }

}