package com.li.mykotlinapp.util

import java.text.SimpleDateFormat
import java.util.*


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.util
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/5/24
 *@Copyright:(C)2021 . All rights reserved.
 *************************************************************************/
object TimeUtil {

    fun timeMillisToTime(time:Long):String{
        return SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time)
    }

    fun getCurrentSecond(): Long? {
        //1、取得本地时间：
        val cal = Calendar.getInstance()
        //2、取得时间偏移量：
        val zoneOffset = cal[Calendar.ZONE_OFFSET]
        //3、取得夏令时差：
        val dstOffset = cal[Calendar.DST_OFFSET]
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
        return cal.timeInMillis / 1000
    }

}