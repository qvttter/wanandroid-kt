package com.li.mykotlinapp.util

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Build
import android.preference.Preference
import android.util.DisplayMetrics
import android.view.*
import java.lang.reflect.ParameterizedType

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.util
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/7/9
 *@Copyright:(C)2020 . All rights reserved.
 *************************************************************************/
class CommonUtils {
    /**
     * save cookie string
     */
    companion object {
        fun <T> getClass(t: Any): Class<T> {
            // 通过反射 获取父类泛型 (T) 对应 Class类
            return (t.javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[0]
                    as Class<T>
        }


        @Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE")
        fun saveCookie(url: String?, domain: String?, cookies: String) {
//            url ?: return
//            var spUrl: String by Preference(url, cookies)
//            @Suppress("UNUSED_VALUE")
//            spUrl = cookies
//            domain ?: return
//            var spDomain: String by Preference(domain, cookies)
//            @Suppress("UNUSED_VALUE")
//            spDomain = cookies
        }

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

        //获取屏幕原始尺寸高度，包括虚拟功能键高度
        fun getDpi(context: Context): Int {
            var dpi = 0
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val displayMetrics = DisplayMetrics()
            val c: Class<*>
            try {
                c = Class.forName("android.view.Display")
                val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
                method.invoke(display, displayMetrics)
                dpi = displayMetrics.heightPixels
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return dpi
        }

        /**
         * 获得屏幕高度
         *
         * @param context
         * @return
         */
        fun getScreenHeight(context: Context): Int {
            val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            return outMetrics.heightPixels
        }

        //判断手机是否显示虚拟按键
        fun isNavigationBarShow(activity: Activity): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                val display = activity.windowManager.defaultDisplay
                val size = Point()
                val realSize = Point()
                display.getSize(size)
                display.getRealSize(realSize)
                val result = realSize.y != size.y
                realSize.y != size.y
            } else {
                val menu = ViewConfiguration.get(activity).hasPermanentMenuKey()
                val back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
                !(menu || back)
            }
        }

        /**
         * 获取 虚拟按键的高度
         *
         * @param context
         * @return
         */
        fun getBottomStatusHeight(context: Context): Int {
            val totalHeight: Int = CommonUtils.getDpi(context)
            val contentHeight: Int = CommonUtils.getScreenHeight(context)
            return totalHeight - contentHeight
        }

        fun isNetworkAvailable(context: Context): Boolean {
            val manager = context.applicationContext.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager
            val info = manager.activeNetworkInfo
            return !(null == info || !info.isAvailable)
        }
    }
}