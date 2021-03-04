package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.apkfuns.logutils.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.bean.bus.MQTTBus
import com.li.mykotlinapp.common.Constants
import com.li.mykotlinapp.service.MQTTService
import com.li.mykotlinapp.service.MyServiceConnection
import com.li.mykotlinapp.util.RxUtil
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_mqtt.*


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/8/17
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class MQTTActivity : BaseActivity() ,IGetMessageCallBack{
    private lateinit var mqttService: MQTTService
    private lateinit var serviceConnection: MyServiceConnection

    override fun getLayout(): Int {
        return R.layout.activity_mqtt
    }

    override fun initData() {
        LiveEventBus
                .get(Constants.MQTT_MSG, MQTTBus::class.java)
                .observeSticky(this, Observer {
                    tv_info.append("mqtt_msg:"+it.msg)
                })

        serviceConnection = MyServiceConnection()
        serviceConnection.setIGetMessageCallBack(this@MQTTActivity)
        val intent = Intent(this, MQTTService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        btn_mqtt.setOnClickListener {
            Observable.just("{\"name\":\"bigOld\"}")
                    .compose(RxUtil.trans_io_io())
                    .subscribe({ t ->
                        MQTTService.publish(t)
                    },{ t: Throwable ->
                        LogUtils.e("error:"+t.message)
                    })
        }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, MQTTActivity().javaClass)
            content.startActivity(intent)
        }
    }

    override fun onDestroy() {
        unbindService(serviceConnection)
        super.onDestroy()
    }

    override fun setMessage(message: String) {
        tv_info.append(message)
        mqttService = serviceConnection.mqttService
        mqttService.toCreateNotification(message)
    }
}