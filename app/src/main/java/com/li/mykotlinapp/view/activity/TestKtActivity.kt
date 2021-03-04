package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.apkfuns.logutils.LogUtils
import com.gengqiquan.result.startActivityWithResult
import com.google.zxing.client.android.CaptureActivity
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.test.MQTTActivity
import com.li.mykotlinapp.test.hellosmartcardActivity
import kotlinx.android.synthetic.main.activity_test.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/11/5
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class TestKtActivity : BaseActivity() {

    override fun getLayout(): Int {
        return R.layout.activity_test
    }

    override fun initData() {
        initEvent()
    }

    private fun initEvent() {
        tv_text.setOnClickListener {
            tv_text.text = ""
        }

        btn_object_box.setOnClickListener {
            ObjectActivity.start(mContext)
        }
        btn_hellosmartcard.setOnClickListener {
            hellosmartcardActivity.start(mContext)
        }

        btn_OmapiActivity.setOnClickListener {
            OmapiActivity.start(mContext)
        }
        btn_mqtt.setOnClickListener {
            MQTTActivity.start(mContext)
        }
        btn_excel.setOnClickListener {
            ExcelActivity.start(mContext)
        }
        btn_zxing.setOnClickListener {
            startActivityWithResult<ScanActivity>()
                    .filter { it.isOK }
                    .subscribe({ result ->
                        val scanResult = result.data.getStringExtra("result")
                        LogUtils.e("扫码结果:"+result.data)
                        tv_text.text = "扫码结果:$scanResult"


                    }) { e ->
                        e.printStackTrace()
                        tv_text.text = "扫码异常:"+e.message
                        LogUtils.e("扫码异常:"+e.message)
                    }
        }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, TestKtActivity().javaClass)
            content.startActivity(intent)
        }
    }
}