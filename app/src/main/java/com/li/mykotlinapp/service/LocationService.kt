package com.li.mykotlinapp.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import com.apkfuns.logutils.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.li.mykotlinapp.bean.bus.LocationBus
import com.li.mykotlinapp.common.Constants
import com.li.mykotlinapp.util.RxLocationTool

/************************************************************************
 *@Project: yiselie
 *@Package_Name: com.easyway.ipu.service
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/1
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class LocationService: Service() {
    private lateinit var mContext:Context

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        LogUtils.e("LocationService")
        init()
    }

    private fun init(){
        LiveEventBus
            .get(Constants.BUS_LOCATION, LocationBus::class.java)
            .observeForever {
                RxLocationTool.registerLocationOnce(
                    mContext,
                    5000,
                    0,
                    object : RxLocationTool.OnLocationChangeListener {
                        override fun getLastKnownLocation(location: Location) {
                            LogUtils.e("getLastKnownLocation:" + location.latitude + ";" + location.longitude)
                        }

                        override fun onLocationChanged(location: Location) {
                        }

                        override fun onStatusChanged(
                            provider: String?,
                            status: Int,
                            extras: Bundle?
                        ) {
                        }
                    })
            }
    }

}