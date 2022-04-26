package com.li.mykotlinapp.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.os.Bundle
import android.os.IBinder
import com.apkfuns.logutils.LogUtils


/************************************************************************
 *@Project: yiselie
 *@Package_Name: com.easyway.ipu.service
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/1
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class GpsService : Service(), LocationListener {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val mLocationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        mLocationManager.requestLocationUpdates(GPS_PROVIDER, 5000, 0f, this)
        return START_STICKY
    }

    override fun onLocationChanged(location: Location) {
        LogUtils.e("location:longitude:"+location.longitude+";latitude"+location.latitude)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        LogUtils.e( "Provider Status: $status")
    }
}