package com.li.mykotlinapp.common

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.jeremyliao.liveeventbus.LiveEventBus
import com.li.mykotlinapp.biz.db.ObjectBox
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Provider
import java.security.Security


//import com.tencent.bugly.Bugly

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.common
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/8
 
 *************************************************************************/
class App : Application() {
    init {
        instance = this
    }

    /**
     * 初始化二维码解密类，需要登录后从后台获取秘钥map
     */

    override fun onCreate() {
        super.onCreate()
        ObjectBox.init(this)

//        Bugly.init(applicationContext, "d0cd42083f", true)

        LiveEventBus
            .config()
            .autoClear(true)

        setupBouncyCastle()
    }

    fun setupBouncyCastle() {
        val provider: Provider = Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)
            ?: // Web3j will set up the provider lazily when it's first used.
            return
        if (provider.javaClass == BouncyCastleProvider::class.java) {
            // BC with same package name, shouldn't happen in real life.
            return
        }
        // Android registers its own BC provider. As it might be outdated and might not include
        // all needed ciphers, we substitute it with a known BC bundled in the app.
        // Android's BC has its package rewritten to "com.android.org.bouncycastle" and because
        // of that it's possible to have another BC implementation loaded in VM.

        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME)
        Security.insertProviderAt(BouncyCastleProvider(), 1)
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        lateinit var instance: App
    }
}