package com.li.mykotlinapp.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.IBinder;
import android.view.Display;
import android.view.WindowManager;

import com.li.mykotlinapp.view.activity.SecondScreenPresentation;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.service
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/12/15
 *@Copyright:(C)2021 . All rights reserved.
 *************************************************************************/
public class MultiScreenService extends Service {
    /**  屏幕管理器 **/
    private DisplayManager mDisplayManager;
    /**  屏幕数组 **/
    private Display[] displays;
    /**  第二块屏 **/
    private SecondScreenPresentation presentation;


    @Override
    public IBinder onBind(Intent intent) {
        return new MultiScreenBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initPresentation();
    }

    /** 初始化第二块屏幕 **/
    private void initPresentation(){
        if(null==presentation){
            mDisplayManager = (DisplayManager) this.getSystemService(Context.DISPLAY_SERVICE);
            displays = mDisplayManager.getDisplays();
            if(displays.length > 1){
                // displays[1]是副屏
                presentation = new SecondScreenPresentation(this, displays[1]);
                presentation.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            }
        }
    }

    /** 显示第二块屏 **/
    public void showSearchPresentation(){
        presentation.show();
    }


    public class MultiScreenBinder extends Binder {
        public MultiScreenService getService(){
            return MultiScreenService.this;
        }
    }

}
