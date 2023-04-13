package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.view.MotionEvent
import android.view.View
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import kotlinx.android.synthetic.main.activity_dispatch_event.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/6/30
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
class DispatchEventActivity :BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_dispatch_event
    }

    override fun initData() {
        btn_intercept_view_group.setOnClickListener {
            tv_info.text = "click!"
        }
4
        window.decorView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                LogUtils.e("window.decorView setOnTouchListener,action:"+p1?.action)
                return false
            }
        })

        btn_intercept_view_group.setOnTouchListener { view, motionEvent ->
            LogUtils.e("btn_intercept_view_group setOnTouchListener,motionEvent:"+motionEvent?.action)
            false
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        LogUtils.e("this is activity dispatchTouchEvent:"+ev?.action)
        return super.dispatchTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        LogUtils.e("this is activity onTouchEvent:"+event?.action)
        return super.onTouchEvent(event)
    }

    companion object {
            fun start(content: Context){
                val intent = Intent(content,DispatchEventActivity().javaClass)
                content.startActivity(intent)
            }
        }
}