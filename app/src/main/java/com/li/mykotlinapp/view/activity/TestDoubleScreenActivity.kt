package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import android.app.Presentation

import android.view.Display

import android.media.MediaRouter
import android.hardware.display.DisplayManager
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_doubel_screen.*
import kotlin.random.Random
import com.li.mykotlinapp.service.MultiScreenService
import android.content.ComponentName

import android.os.IBinder

import android.content.ServiceConnection
import com.li.mykotlinapp.service.MultiScreenService.MultiScreenBinder


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/12/13
 *@Copyright:(C)2021 . All rights reserved.
 *************************************************************************/
class TestDoubleScreenActivity : BaseActivity() {
    private lateinit var presentation: SecondScreenPresentation
    private var multiScreenService: MultiScreenService? = null


    override fun getLayout(): Int {
        return R.layout.activity_doubel_screen
    }

    override fun initData() {
        var mDisplayManager = this.getSystemService(DISPLAY_SERVICE) as DisplayManager

        btn_open_second_screen.setOnClickListener {
            val intent = Intent(this, MultiScreenService::class.java)
            this.bindService(intent, serviceConnection, BIND_AUTO_CREATE)
//            var displays = mDisplayManager.displays
//            tv_log.setText("屏幕数量:" + displays.size)
//            if (displays.size > 1) {
//                // displays[1]是副屏
//                presentation = SecondScreenPresentation(this, displays[1])
//                presentation.window!!.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
//                presentation.show()
//            }
//            presentation.changeSecondScreenText("随机数字：" + Random().nextInt(11))
        }

        btn_close_second_screen.setOnClickListener {

        }
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            multiScreenService = (service as MultiScreenBinder).service
            //显示第二块屏幕
            multiScreenService!!.showSearchPresentation()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            //恢复置空
            multiScreenService = null
        }
    }


    companion object {
        fun start(content: Context) {
            val intent = Intent(content, TestDoubleScreenActivity().javaClass)
            content.startActivity(intent)
        }
    }
}