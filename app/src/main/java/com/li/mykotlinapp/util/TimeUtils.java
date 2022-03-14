package com.li.mykotlinapp.util;

import com.apkfuns.logutils.LogUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.util
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/6/3
 *@Copyright:(C)2021 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
public class TimeUtils {
    //形如20210521080000这样格式的时间转换成正常时间
    public static String stringToIsraelTimeShort(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));

        SimpleDateFormat sdfIsrael = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfIsrael.setTimeZone(TimeZone.getTimeZone("Asia/Tel_Aviv"));

        Date date = null;
        try {
            date = sdf.parse(time);
            String IsraelTime = sdfIsrael.format(date);
            LogUtils.e("stringToIsraelTimeShort结果:"+IsraelTime);
            return IsraelTime;
        } catch (Exception ex) {
            ex.printStackTrace();
            LogUtils.e("时间转换出错。:" + ex.getMessage());
            return "";
        }
    }

    public static String getTimeGMTNow() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("gmt"));
        return sdf.format(new Date());
    }


}
