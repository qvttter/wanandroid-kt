package com.li.mykotlinapp.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.li.mykotlinapp.test.IGetMessageCallBack;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.service
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/8/18 
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved. 
 *************************************************************************/
public class MyServiceConnection implements ServiceConnection {
    private MQTTService mqttService;
    private IGetMessageCallBack iGetMessageCallBack;

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        mqttService = ((MQTTService.CustomBinder)iBinder).getService();
        mqttService.setIGetMessageCallBack(iGetMessageCallBack);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }

    public MQTTService getMqttService(){
        return mqttService;
    }

    public void setIGetMessageCallBack(IGetMessageCallBack IGetMessageCallBack){
        this.iGetMessageCallBack = IGetMessageCallBack;
    }
}
