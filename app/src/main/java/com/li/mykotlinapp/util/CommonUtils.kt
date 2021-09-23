package com.li.mykotlinapp.util

import java.lang.StringBuilder
import java.lang.reflect.ParameterizedType

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.util
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/7/9
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
object CommonUtils {
    fun <T> getClass(t: Any): Class<T> {
        // 通过反射 获取父类泛型 (T) 对应 Class类
        return (t.javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[0]
                as Class<T>
    }

    /**
     * save cookie string
     */
    fun encodeCookie(cookies: List<String>): String {
        val sb = StringBuilder()
        val set = HashSet<String>()
        cookies
            .map { cookie ->
                cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            }
            .forEach {
                it.filterNot { set.contains(it) }.forEach { set.add(it) }
            }

        val ite = set.iterator()
        while (ite.hasNext()) {
            val cookie = ite.next()
            sb.append(cookie).append(";")
        }

        val last = sb.lastIndexOf(";")
        if (sb.length - 1 == last) {
            sb.deleteCharAt(last)
        }

        return sb.toString()
    }

    @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
    fun saveCookie(url: String?, domain: String?, cookies: String) {
//        url ?: return
//        var spUrl: String by Preference(url, cookies)
//        @Suppress("UNUSED_VALUE")
//        spUrl = cookies
//        domain ?: return
//        var spDomain: String by Preference(domain, cookies)
//        @Suppress("UNUSED_VALUE")
//        spDomain = cookies
    }
}