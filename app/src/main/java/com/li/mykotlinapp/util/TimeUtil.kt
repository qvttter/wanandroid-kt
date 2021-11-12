package com.li.mykotlinapp.util

import java.text.SimpleDateFormat


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.util
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/5/24
 *@Copyright:(C)2021 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
object TimeUtil {

    fun timeMillisToTime(time:Long):String{
        return SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(time)
    }
}