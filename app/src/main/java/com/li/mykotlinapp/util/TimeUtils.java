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

    /**
     * 当前秒数，UTC时区
     */
    public static Long getCurrentSecond(){
        //1、取得本地时间：
        java.util.Calendar cal = java.util.Calendar.getInstance();
        //2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTimeInMillis()/1000;
    }


}
