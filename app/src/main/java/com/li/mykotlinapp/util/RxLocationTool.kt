package com.li.mykotlinapp.util

import android.location.LocationManager
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.common.App
import java.io.IOException
import java.text.DecimalFormat
import java.util.*
import kotlin.math.floor

/************************************************************************
 *@Project: yiselie
 *@Package_Name: com.easyway.ipu.utils
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/2/22
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
object  RxLocationTool {
    //圆周率
    const val pi = 3.1415926535897932384626

    //Krasovsky 1940 (北京54)椭球长半轴
    const val a = 6378245.0

    //椭球的偏心率
    const val ee = 0.00669342162296594323
    private var mListener: OnLocationChangeListener? = null
    private var myLocationListener: MyLocationListener? = null
    private var mLocationManager: LocationManager? = null

    /**
     * 判断Gps是否可用
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isGpsEnabled(context: Context): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /**
     * 判断定位是否可用
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    @JvmStatic
    fun isLocationEnabled(context: Context): Boolean {
        val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    /**
     * 打开Gps设置界面
     */
    @JvmStatic
    fun openGpsSettings(context: Context) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    /**
     * 注册
     *
     * 使用完记得调用[.unRegisterLocation]
     *
     * 需添加权限 `<uses-permission android:name="android.permission.INTERNET"/>`
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>`
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>`
     *
     * 如果`minDistance`为0，则通过`minTime`来定时更新；
     *
     * `minDistance`不为0，则以`minDistance`为准；
     *
     * 两者都为0，则随时刷新。
     *
     * @param minTime     位置信息更新周期（单位：毫秒）
     * @param minDistance 位置变化最小距离：当位置距离变化超过此值时，将更新位置信息（单位：米）
     * @param listener    位置刷新的回调接口
     * @return `true`: 初始化成功<br></br>`false`: 初始化失败
     */
    @JvmStatic
    fun registerLocation(context: Context, minTime: Long, minDistance: Long, listener: OnLocationChangeListener?): Boolean {
        if (listener == null) {
            return false
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return false
        }
        mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mListener = listener
        if (!isLocationEnabled(context)) {
            ToastUtil.longToast(App.instance,"Unable to locate, please turn on location services")
            return false
        }
        val provider = mLocationManager!!.getBestProvider(criteria, true)
        val location = mLocationManager!!.getLastKnownLocation(provider!!)
        if (location != null) {
            listener.getLastKnownLocation(location)
        }
        if (myLocationListener == null) {
            myLocationListener = MyLocationListener()
        }
        mLocationManager!!.requestLocationUpdates(provider, minTime, minDistance.toFloat(), myLocationListener!!)
        return true
    }

    @JvmStatic
    fun registerLocationOnce(context: Context, minTime: Long, minDistance: Long, listener: OnLocationChangeListener?): Boolean {
        if (listener == null) {
            return false
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((context as Activity), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 1)
            return false
        }
        mLocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mListener = listener
        if (!isLocationEnabled(context)) {
            ToastUtil.longToast(App.instance,"Unable to locate, please turn on location services")
            return false
        }
        val provider = mLocationManager!!.getBestProvider(criteria, true)
        val location = mLocationManager!!.getLastKnownLocation(provider!!)
        LogUtils.e("location:$location")
        if (location != null) {
            listener.getLastKnownLocation(location)
            unRegisterLocation()
            return true
        }
        if (myLocationListener == null) {
            myLocationListener = MyLocationListener()
        }
        mLocationManager!!.requestLocationUpdates(provider, minTime, minDistance.toFloat(), myLocationListener!!)
        return true
    }

    /**
     * 注销
     */
    @JvmStatic
    fun unRegisterLocation() {
        if (mLocationManager != null) {
            if (myLocationListener != null) {
                mLocationManager!!.removeUpdates(myLocationListener!!)
                myLocationListener = null
            }
            mLocationManager = null
        }
    }//设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
    //设置是否要求速度
    // 设置是否允许运营商收费
    //设置是否需要方位信息
    //设置是否需要海拔信息
    // 设置对电源的需求

    /**
     * 设置定位参数
     *
     * @return [Criteria]
     */
    private val criteria: Criteria
        private get() {
            val criteria = Criteria()
            //设置定位精确度 Criteria.ACCURACY_COARSE比较粗略，Criteria.ACCURACY_FINE则比较精细
            criteria.accuracy = Criteria.ACCURACY_FINE
            //设置是否要求速度
            criteria.isSpeedRequired = true
            // 设置是否允许运营商收费
            criteria.isCostAllowed = true
            //设置是否需要方位信息
            criteria.isBearingRequired = true
            //设置是否需要海拔信息
            criteria.isAltitudeRequired = true
            // 设置对电源的需求
            criteria.powerRequirement = Criteria.POWER_LOW
            return criteria
        }

    /**
     * 根据经纬度获取地理位置
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return [Address]
     */
    @JvmStatic
    fun getAddress(context: Context?, latitude: Double, longitude: Double): Address? {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                return addresses[0]
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 根据经纬度获取所在国家
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在国家
     */
    @JvmStatic
    fun getCountryName(context: Context?, latitude: Double, longitude: Double): String {
        val address = getAddress(context, latitude, longitude)
        return if (address == null) "unknown" else address.countryName
    }

    /**
     * 根据经纬度获取所在地
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在地
     */
    @JvmStatic
    fun getLocality(context: Context?, latitude: Double, longitude: Double): String {
        val address = getAddress(context, latitude, longitude)
        return if (address == null) "unknown" else address.locality
    }

    /**
     * 根据经纬度获取所在街道
     *
     * @param context   上下文
     * @param latitude  纬度
     * @param longitude 经度
     * @return 所在街道
     */
    @JvmStatic
    fun getStreet(context: Context?, latitude: Double, longitude: Double): String {
        val address = getAddress(context, latitude, longitude)
        return if (address == null) "unknown" else address.getAddressLine(0)
    }
    //------------------------------------------坐标转换工具start--------------------------------------
    /**
     * GPS坐标 转换成 角度
     * 例如 113.202222 转换成 113°12′8″
     *
     * @param location
     * @return
     */
    @JvmStatic
    fun gpsToDegree(location: Double): String {
        val degree = floor(location)
        val minute_temp = (location - degree) * 60
        val minute = floor(minute_temp)
        //        double second = Math.floor((minute_temp - minute)*60);
        val second = DecimalFormat("#.##").format((minute_temp - minute) * 60)
        return (degree.toInt()).toString() + "°" + minute.toInt() + "′" + second + "″"
    }




    //===========================================坐标转换工具end====================================
    @JvmStatic
    fun isMove(location: Location, preLocation: Location?): Boolean {
        val isMove: Boolean
        if (preLocation != null) {
            val speed = location.speed * 3.6
            val distance = location.distanceTo(preLocation).toDouble()
            val compass = Math.abs(preLocation.bearing - location.bearing).toDouble()
            val angle: Double
            angle = if (compass > 180) {
                360 - compass
            } else {
                compass
            }
            isMove = if (speed != 0.0) {
                if (speed < 35 && distance > 3 && distance < 10) {
                    angle > 10
                } else {
                    speed < 40 && distance > 10 && distance < 100 ||
                            speed < 50 && distance > 10 && distance < 100 ||
                            speed < 60 && distance > 10 && distance < 100 ||
                            speed < 9999 && distance > 100
                }
            } else {
                false
            }
        } else {
            isMove = true
        }
        return isMove
    }

    interface OnLocationChangeListener {
        /**
         * 获取最后一次保留的坐标
         *
         * @param location 坐标
         */
        fun getLastKnownLocation(location: Location)

        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        fun onLocationChanged(location: Location)

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider可选包
         */
        fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) //位置状态发生改变
    }

    class MyLocationListener : LocationListener {
        /**
         * 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
         *
         * @param location 坐标
         */
        override fun onLocationChanged(location: Location) {
            if (mListener != null) {
                mListener!!.onLocationChanged(location)
            }
        }

        /**
         * provider的在可用、暂时不可用和无服务三个状态直接切换时触发此函数
         *
         * @param provider 提供者
         * @param status   状态
         * @param extras   provider可选包
         */
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
            if (mListener != null) {
                mListener!!.onStatusChanged(provider, status, extras)
            }
            when (status) {
                LocationProvider.AVAILABLE -> LogUtils.e("onStatusChanged", "当前GPS状态为可见状态")
                LocationProvider.OUT_OF_SERVICE -> LogUtils.e("onStatusChanged", "当前GPS状态为服务区外状态")
                LocationProvider.TEMPORARILY_UNAVAILABLE -> LogUtils.e("onStatusChanged", "当前GPS状态为暂停服务状态")
                else -> {
                }
            }
        }

        /**
         * provider被enable时触发此函数，比如GPS被打开
         */
        override fun onProviderEnabled(provider: String) {}

        /**
         * provider被disable时触发此函数，比如GPS被关闭
         */
        override fun onProviderDisabled(provider: String) {}
    }
}