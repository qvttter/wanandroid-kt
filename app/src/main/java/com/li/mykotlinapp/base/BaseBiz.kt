package com.li.mykotlinapp.base

import android.content.Context
import com.apkfuns.logutils.LogUtils
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
import okhttp3.*
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.StringBuilder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23

 *************************************************************************/
open class BaseBiz {
    companion object {
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
    private val cookieJar by lazy {
        PersistentCookieJar(
            SetCookieCache(),
            SharedPrefsCookiePersistor(App.instance)
        )
    }


    constructor() {
        mContext = App.instance
//        val cookieJar: PersistentCookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(mContext))
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(20000, TimeUnit.MILLISECONDS)
            .readTimeout(20000, TimeUnit.MILLISECONDS)
            .cookieJar(cookieJar)
            .addInterceptor { chain ->
                var request = chain.request()
                if (!CommonUtils.isNetworkAvailable(mContext)) {
                    request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
                }
                val response = chain.proceed(request)
                if (!CommonUtils.isNetworkAvailable(mContext)) {
                    val maxAge = 60 * 60
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
                response
            }
            .addInterceptor(ResponseCookieInterceptor())
            .addInterceptor(SetResponseCookieInterceptor())
            .build()
        okHttpClient.dispatcher().maxRequestsPerHost = 10
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
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
                )) && response.headers(SET_COOKIE_KEY).isNotEmpty()
            ) {
                val cookies = response.headers(SET_COOKIE_KEY)
                val cookie = CommonUtils.encodeCookie(cookies)
                LogUtils.e("requestUrl, domain, cookie:",requestUrl+";" +domain+";"+ cookie)
//                CommonUtils.Companion.saveCookie(requestUrl, domain, cookie)
                PrefUtil.domain = domain
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
                val spDomain = PrefUtil.domain
                val cookie: String = spDomain.ifEmpty { "" }
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
                com.li.mykotlinapp.base.BaseResult.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                com.li.mykotlinapp.base.BaseResult.Success(response.data)
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
                com.li.mykotlinapp.base.BaseResult.Error(IOException(response.errorMsg))
            } else {
                successBlock?.let { it() }
                com.li.mykotlinapp.base.BaseResult.Success(response.data)
            }
        }
    }


}