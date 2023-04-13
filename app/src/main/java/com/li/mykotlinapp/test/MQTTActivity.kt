package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import com.apkfuns.logutils.LogUtils
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.bean.MQTTBean
import com.li.mykotlinapp.bean.bus.MQTTBus
import com.li.mykotlinapp.common.Constants
import com.li.mykotlinapp.service.MQTTService
import com.li.mykotlinapp.service.MyServiceConnection
import com.li.mykotlinapp.util.RxUtil
import com.li.mykotlinapp.view.activity.CommonWebViewActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_mqtt.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/5/13
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
class MQTTActivity: BaseActivity() ,IGetMessageCallBack {
    private lateinit var mqttService: MQTTService
    private lateinit var serviceConnection: MyServiceConnection

    override fun getLayout(): Int {
        return R.layout.activity_mqtt
    }

    override fun initData() {
        LiveEventBus
            .get(Constants.MQTT_MSG, MQTTBus::class.java)
            .observeSticky(this) {
                LogUtils.e("mqtt msg:"+it.msg)
                if(it.msg.startsWith("http")||(it.msg.startsWith("www"))){
                    CommonWebViewActivity.start(mContext, it.msg, "mqtt")
                }
//
//                tv_info.append("mqtt_msg:" + it.msg)
            }

        serviceConnection = MyServiceConnection()
        serviceConnection.setIGetMessageCallBack(this@MQTTActivity)
        val intent = Intent(this, MQTTService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

        btn_mqtt.setOnClickListener {
            Observable.just("{\"telemetry\":\"35\"}")
                .compose(RxUtil.trans_io_io())
                .subscribe({ t ->
                    MQTTService.publish(t)
                },{ t: Throwable ->
                    LogUtils.e("error:"+t.message)
                })
        }

        btn_request_RPC_Topic.setOnClickListener {
            Observable.just("{\"key\":\"value\"}")
                .compose(RxUtil.trans_io_io())
                .subscribe({ t ->
                    MQTTService.requestRPCTopic(t)
                },{ t: Throwable ->
                    LogUtils.e("error:"+t.message)
                })
        }

        btn_device_provisioning.setOnClickListener {
            Observable.just("{\"deviceName\": \"redmi_k40\",\"provisionDeviceKey\": \""+MQTTService.device_profile_key+"\"," +
                    "\"provisionDeviceSecret\": \""+MQTTService.device_profile_secret+"\"}")
                .compose(RxUtil.trans_io_io())
                .subscribe({ t ->
                    MQTTService.deviceProvisioning(t)
                },{ t: Throwable ->
                    LogUtils.e("error:"+t.message)
                })
        }

        btn_provisionRequest.setOnClickListener {
            Observable.just("{\"deviceName\": \"redmi_k40\",\"provisionDeviceKey\": \""+MQTTService.device_profile_key+"\"," +
                    "\"provisionDeviceSecret\": \""+MQTTService.device_profile_secret+"\"}")
                .compose(RxUtil.trans_io_io())
                .subscribe({ t ->
                    MQTTService.provisionRequest(t)
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
        var bean = Gson().fromJson(message,MQTTBean::class.java)
        if (bean.params.url.startsWith("http")||bean.params.url.startsWith("www")){
            CommonWebViewActivity.start(mContext, bean.params.url, "mqtt")
        }
        tv_info.append(message)
        mqttService = serviceConnection.mqttService
        mqttService.toCreateNotification(message)
    }
}