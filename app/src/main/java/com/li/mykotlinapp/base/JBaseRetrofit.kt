package com.li.mykotlinapp.base

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.li.mykotlinapp.common.App
import com.li.mykotlinapp.common.Constants
import com.li.mykotlinapp.util.CommonUtils
import com.li.mykotlinapp.util.PrefUtil
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/25
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
open class JBaseRetrofit {
    companion object{
        private const val TAG = "RetrofitHelper"
        private const val CONTENT_PRE = "OkHttp: "
        private const val SAVE_USER_LOGIN_KEY = "user/login"
        private const val SAVE_USER_REGISTER_KEY = "user/register"
        private const val SET_COOKIE_KEY = "set-cookie"
        private const val COOKIE_NAME = "Cookie"
        private const val CONNECT_TIMEOUT = 30L
        private const val READ_TIMEOUT = 10L
    }
    var retrofit: Retrofit
    var mContext: Context
    var okHttpClient: OkHttpClient

    constructor() {
        mContext = App.instance
        val cookieJar: PersistentCookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(mContext))
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20000, TimeUnit.MILLISECONDS)
            .readTimeout(20000, TimeUnit.MILLISECONDS)
            .cookieJar(cookieJar)
//            .addInterceptor(ResponseCookieInterceptor())
//            .addInterceptor(SetResponseCookieInterceptor())
            .build()
        okHttpClient.dispatcher().maxRequestsPerHost = 10
        retrofit = Retrofit.Builder()
            .baseUrl(PrefUtil.jUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    internal class ResponseCookieInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(it: Interceptor.Chain): Response {
            val request = it.request()
            val response = it.proceed(request)
            val requestUrl = request.url().toString()
            val domain = request.url().host()
            // set-cookie maybe has multi, login to save cookie
            if ((requestUrl.contains(SAVE_USER_LOGIN_KEY) || requestUrl.contains(
                    SAVE_USER_REGISTER_KEY
                ))
                && !response.headers(SET_COOKIE_KEY).isEmpty()) {
                val cookies = response.headers(SET_COOKIE_KEY)
                val cookie = CommonUtils.encodeCookie(cookies)
                CommonUtils.Companion.saveCookie(requestUrl, domain, cookie)
            }
            return response
        }
    }

    internal class SetResponseCookieInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(it: Interceptor.Chain): Response {
            val request = it.request()
            val builder = request.newBuilder()
            val domain = request.url().host()
            // get domain cookie
            if (domain.isNotEmpty()) {
                val spDomain= PrefUtil.domain
                val cookie: String = if (spDomain.isNotEmpty()) spDomain else ""
                if (cookie.isNotEmpty()) {
                    builder.addHeader(COOKIE_NAME, cookie)
                }
            }
            return it.proceed(builder.build())
        }
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

    suspend fun <T : Any> executeResponse(
        response: BaseResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): com.li.mykotlinapp.base.BaseResult<T> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                BaseResult.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                BaseResult.Success(response.data)
            }
        }
    }

    suspend fun <T : Any> executeListResponse(
        response: BaseListResponse<T>, successBlock: (suspend CoroutineScope.() -> Unit)? = null,
        errorBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): com.li.mykotlinapp.base.BaseResult<List<T>> {
        return coroutineScope {
            if (response.errorCode == -1) {
                errorBlock?.let { it() }
                BaseResult.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                BaseResult.Success(response.data)
            }
        }
    }
}