package com.li.mykotlinapp.util

import android.content.Context

import com.li.mykotlinapp.common.App
import com.li.mykotlinapp.common.Constants

/**
 * SharePreference封装
 *
 * @author Kevin
 * @date 2015-10-17
 */
object PrefUtil {
    private val mContext = App.instance

    /**
     * j网址
     * "https://www.javbus.com"-基本网址，需要翻墙
     */
    var jUrl: String?
        get() = getString(mContext, Constants.J_URL, "")
        set(url) = setString(mContext, Constants.J_URL, url!!)


    /**
     * 用户名
     *
     * @param userName
     */
    var userName: String?
        get() = getString(mContext, Constants.USERNAME, "")
        set(userName) = setString(mContext, Constants.USERNAME, userName!!)

    /**
     * 密码
     *
     * @param pwd
     */
    var pwd: String?
        get() = getString(mContext, Constants.PASSWORD, "")
        set(pwd) = setString(mContext, Constants.PASSWORD, pwd!!)

    /**
     * domain
     *
     * @param domain
     */
    var domain: String
        get() = getString(mContext, Constants.DOMAIN, "")!!
        set(domain) = setString(mContext, Constants.DOMAIN, domain)

    /**
     * 登录状态
     * */
    var flagLogin: Boolean
        get() = getBoolean(mContext, Constants.FLAG_LOGIN, false)
        set(b) = setBoolean(mContext, Constants.FLAG_LOGIN, b)

    private fun getBoolean(ctx: Context, key: String, defValue: Boolean): Boolean {
        val sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE)
        return sp.getBoolean(key, defValue)
    }

    private fun setBoolean(ctx: Context, key: String, value: Boolean) {
        val sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE)
        sp.edit().putBoolean(key, value).commit()
    }

    private fun setString(ctx: Context, key: String, value: String) {
        val sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE)
        sp.edit().putString(key, value).commit()
    }

    private fun getString(ctx: Context, key: String, defValue: String): String? {
        val sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE)
        return sp.getString(key, defValue)
    }

    private fun setInt(ctx: Context, key: String, value: Int) {
        val sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE)
        sp.edit().putInt(key, value).commit()
    }

    private fun getInt(ctx: Context, key: String, defValue: Int): Int {
        val sp = ctx.getSharedPreferences("config",
                Context.MODE_PRIVATE)
        return sp.getInt(key, defValue)
    }
}