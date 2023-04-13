package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import com.ailiwean.core.Result
import com.apkfuns.logutils.LogUtils
import com.ewivt.redline.datasecurity.DataSignature
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.widget.CusScanView
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.include_toolbar.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 3/4/21
 *@Copyright:(C)2021 . All rights reserved.
 *************************************************************************/
class ScanActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_scan
    }

    override fun initData() {
        tool_bar.title="scan"
        tool_bar.setNavigationOnClickListener { finish() }

        zxingview.synchLifeStart(this)
        zxingview.setResultListener(object : CusScanView.OnResultListener {
            override fun onResult(content: Result) {
                LogUtils.e("扫码结果："+content.text)
                var s= content.text.substring(8,content.text.length)
                LogUtils.e("substring结果："+s)
                var cipherText =  DataSignature.dataDecryption("kn9jdKx2MmC8eyEiq4tanQ==",s)
                LogUtils.e("解码结果："+cipherText)
finish()

            }
        })
    }


    companion object {
        fun start(content: Context) {
            val intent = Intent(content, ScanActivity().javaClass)
            content.startActivity(intent)
        }
    }
}