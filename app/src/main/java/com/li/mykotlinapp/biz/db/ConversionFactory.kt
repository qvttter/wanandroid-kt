package com.li.mykotlinapp.biz.db

import android.text.TextUtils
import androidx.room.TypeConverter
import java.sql.Date


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.biz.db
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/12/6
 
 *************************************************************************/
class ConversionFactory {
    @TypeConverter
    open fun arrayToString(array: Array<String>): String {
        if (array == null || array.size === 0) {
            return ""
        }

        val builder = StringBuilder(array[0])
        for (i in 1..array.size - 1) {
            builder.append(",").append(array[i])
        }
        return builder.toString()
    }

    @TypeConverter
    open fun StringToArray(value: String): Array<String>? {
        return if (TextUtils.isEmpty(value)) null else value.split(",").toTypedArray()

    }
}