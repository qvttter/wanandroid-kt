package com.li.mykotlinapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.li.mykotlinapp.bean.MQTTBean;
import com.li.mykotlinapp.bean.MQTTProvisionResponse;
import com.li.mykotlinapp.bean.bus.MQTTBus;
import com.li.mykotlinapp.common.Constants;
import com.li.mykotlinapp.test.IGetMessageCallBack;
import com.li.mykotlinapp.util.RxUtil;
import com.li.mykotlinapp.util.TimeUtils;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.service
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/5/13
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
public class MQTTService extends Service {
    public static final String TAG = MQTTService.class.getSimpleName();

    public static final String device_profile_key = "vdqdq7mp8awvjzi1p532";
    public static final String device_profile_secret = "etsv5p0axneaxfqu7zp7";

    public static final String provision_username = "provision";

    private static MqttAndroidClient client;
    private MqttConnectOptions conOpt;

    private String host = "tcp://10.100.103.33:31786";
    //一开始使用如下userName，等到初始化收到后端消息后，更换userName
    private String userName = provision_username;
    private String passWord = "";
    //一开始使用如下provisionRequestTopic和监听provisionResponseTopic，接受后端给的token作为新的username
    private static String provisionRequestTopic = "/provision/request";
    private static String provisionResponseTopic = "/provision/response";

    private static String telemetryTopic = "v1/devices/me/telemetry";
    private static String RPCRequestTopic = "v1/devices/me/rpc/request/";
    private static String RPCResponseTopic = "v1/devices/me/rpc/response/";
    private static String RPCSubscribeTopic = "v1/devices/me/rpc/request/+";      //向服务端订阅
    private static String provisionTopic = "/provision";      //向服务端订阅


    private String currentRequestId = "";

    public static final String clientId = UUID.randomUUID().toString();//客户端标识
    private IGetMessageCallBack iGetMessageCallBack;


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    private void init() {
        // 服务器地址（协议+地址+端口号）
        client = new MqttAndroidClient(this, host, clientId);
        // 设置MQTT监听并且接受消息
        client.setCallback(mqttCallback);

        conOpt = new MqttConnectOptions();
        // 清除缓存
        conOpt.setCleanSession(true);
        // 设置超时时间，单位：秒
        conOpt.setConnectionTimeout(10);
        // 心跳包发送间隔，单位：秒
        conOpt.setKeepAliveInterval(20);
        // 用户名
        conOpt.setUserName(userName);
        // 密码
        conOpt.setPassword(passWord.toCharArray());     //将字符串转换为字符串数组

        LogUtils.e("userName:" + userName);
        // last will message
        boolean doConnect = true;
        String message = "{\"terminal_uid\":\"" + clientId + "\"}";
        Log.e(getClass().getName(), "message是:" + message);
        String topic = provisionRequestTopic;
        Integer qos = 0;
        Boolean retained = false;
        if ((!message.equals("")) || (!topic.equals(""))) {
            // 最后的遗嘱
            // MQTT本身就是为信号不稳定的网络设计的，所以难免一些客户端会无故的和Broker断开连接。
            //当客户端连接到Broker时，可以指定LWT，Broker会定期检测客户端是否有异常。
            //当客户端异常掉线时，Broker就往连接时指定的topic里推送当时指定的LWT消息。
            try {
                conOpt.setWill(topic, message.getBytes(), qos.intValue(), retained.booleanValue());
            } catch (Exception e) {
                LogUtils.e("");
                Log.i(TAG, "Exception Occured", e);
                doConnect = false;
                iMqttActionListener.onFailure(null, e);
            }
        }

        if (doConnect) {
            doClientConnection();
        }

    }


    @Override
    public void onDestroy() {
        stopSelf();
        try {
            client.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * 连接MQTT服务器
     */
    private void doClientConnection() {
        if (!client.isConnected() && isConnectIsNormal()) {
            try {
                LogUtils.e("开始连接");
                client.connect(conOpt, null, iMqttActionListener);
            } catch (MqttException e) {
                e.printStackTrace();
                LogUtils.e("mqtt 连接失败;" + e.getMessage());
                iGetMessageCallBack.setMessage("mqtt 连接失败;" + e.getMessage());
            }
        }

    }

    // MQTT是否连接成功
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {
        @Override
        public void onSuccess(IMqttToken arg0) {
            try {
                // 订阅myTopic话题
                LogUtils.e("mqtt 连接成功 开始订阅provisionResponseTopic");
                //当还未获取到token，监听后端分配的token
                if (userName.equals(provision_username)) {
                    client.subscribe(provisionResponseTopic, 1);

                    provisionRequest("{\"deviceName\": \"redmi_k40\",\"provisionDeviceKey\": \"" + MQTTService.device_profile_key + "\"," +
                            "\"provisionDeviceSecret\": \"" + MQTTService.device_profile_secret + "\"}");
                } else {
                    //订阅RPC
                    subscribeRPC();
//                    client.subscribe(RPCResponseTopic, 1);
                }
            } catch (MqttException e) {
                e.printStackTrace();
                LogUtils.e("mqtt 订阅失败");
            }
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            LogUtils.e("mqtt 连接失败;" + arg1.getMessage());
            iGetMessageCallBack.setMessage("mqtt 连接失败;" + arg1.getMessage());

            // 连接失败，重连
        }
    };

    // MQTT监听并且接受消息
    private MqttCallback mqttCallback = new MqttCallback() {
        @Override
        public void messageArrived(String topic, MqttMessage message) throws Exception {
            LogUtils.e("mqtt收到数据，topic：" + topic + ";getQos:" + message.getQos() + ";getPayload:" + new String(message.getPayload())
                    + ";isRetained:" + message.isRetained());
            if (topic.equals(provisionResponseTopic)) {
                String payload = new String(message.getPayload());
                MQTTProvisionResponse response = new Gson().fromJson(payload, MQTTProvisionResponse.class);
                //获取token成功
                userName = response.getCredentialsValue();
                LogUtils.e("mqtt userName：" + userName);
                client.disconnect();
                client = null;
//                    subscribeRPC();
//                    client.disconnect();
                init();
//                doClientConnection();
            } else {
                String str1 = new String(message.getPayload());
                if (iGetMessageCallBack != null) {
                    iGetMessageCallBack.setMessage(str1);
                }
                String[] split = topic.split("/");
                if (split.length > 0) {
                    currentRequestId = split[split.length - 1];
                }
                //需要回一条
                responseCurrentTime();
            }
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {
            LogUtils.e("mqtt deliveryComplete");

        }

        @Override
        public void connectionLost(Throwable arg0) {
            // 失去连接，重连
            LogUtils.e("mqtt connectionLost");
            iGetMessageCallBack.setMessage("mqtt connectionLost");

        }
    };

    public void subscribeRPC() {
        try {
            client.subscribe(RPCSubscribeTopic, 1);
            LogUtils.e("mqtt RPC订阅成功");
            iGetMessageCallBack.setMessage("mqtt RPC订阅成功");
        } catch (MqttException e) {
            e.printStackTrace();
            LogUtils.e("mqtt subscribe RPCSubscribeTopic error:" + e.getMessage());
        }
    }

    /**
     * 判断网络是否连接
     */
    private boolean isConnectIsNormal() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.i(TAG, "MQTT当前网络名称：" + name);
            return true;
        } else {
            Log.i(TAG, "MQTT 没有可用网络");
            return false;
        }
    }

    public static void publish(String msg) {
        String topic = telemetryTopic;
        Integer qos = 0;
        Boolean retained = false;
        try {
            if (client != null) {
                client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
                LogUtils.e("push成功，内容：" + msg);

            }
        } catch (MqttException e) {
            e.printStackTrace();
            LogUtils.e("push出错：" + e.getMessage());
        }
    }

    public static void provisionRequest(String msg) {
        String topic = provisionRequestTopic;
        Integer qos = 0;
        Boolean retained = false;
        try {
            if (client != null) {
                client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
                LogUtils.e("push成功，内容：" + msg);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            LogUtils.e("push出错：" + e.getMessage());
        }
    }


    public static void requestRPCTopic(String msg) {
        String topic = RPCRequestTopic;
        Integer qos = 0;
        Boolean retained = false;
        try {
            if (client != null) {
                client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
                LogUtils.e("push成功，内容：" + msg);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            LogUtils.e("push出错：" + e.getMessage());
        }
    }

    public static void deviceProvisioning(String msg) {
        String topic = provisionTopic;
        Integer qos = 0;
        Boolean retained = false;
        try {
            if (client != null) {
                client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
                LogUtils.e("push成功，内容：" + msg);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            LogUtils.e("push出错：" + e.getMessage());
        }
    }

    public void responseCurrentTime() {
        String topic = RPCResponseTopic + currentRequestId;
        LogUtils.e("topic:" + topic);
        Integer qos = 0;
        Boolean retained = false;
        MQTTBean bean = new MQTTBean("getCurrentTime", new MQTTBean.Params("http://www.bilibili.com/"));
        String msg = new Gson().toJson(bean);
        try {
            if (client != null) {
                client.publish(topic, msg.getBytes(), qos.intValue(), retained.booleanValue());
                LogUtils.e("push成功，内容：" + msg);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            LogUtils.e("push出错：" + e.getMessage());
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return new CustomBinder();
    }

    public class CustomBinder extends Binder {
        public MQTTService getService() {
            return MQTTService.this;
        }
    }

    public void setIGetMessageCallBack(IGetMessageCallBack iGetMessageCallBack) {
        this.iGetMessageCallBack = iGetMessageCallBack;
    }

    public void toCreateNotification(String message) {
        LogUtils.e("toCreateNotification:" + message);

//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(this,MQTTService.class), PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);//3、创建一个通知，属性太多，使用构造器模式
//
//        Notification notification = builder
//                .setTicker("测试标题")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("")
//                .setContentText(message)
//                .setContentInfo("")
//                .setContentIntent(pendingIntent)//点击后才触发的意图，“挂起的”意图
//                .setAutoCancel(true)        //设置点击之后notification消失
//                .build();
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        startForeground(0, notification);
//        notificationManager.notify(0, notification);

    }
}
