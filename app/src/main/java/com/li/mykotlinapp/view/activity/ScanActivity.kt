package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ailiwean.core.Result
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.widget.CusScanView
import kotlinx.android.synthetic.main.activity_scan.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 3/4/21
 *@Copyright:(C)2021 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class ScanActivity : BaseActivity() {
    override fun getLayout(): Int {
        return R.layout.activity_scan
    }

    override fun initData() {
        zxingview.synchLifeStart(this)
        zxingview.setResultListener(object : CusScanView.OnResultListener {
            override fun onResult(content: Result) {
                var resultIntent = Intent()
                var bundle =  Bundle()
                bundle.putString("result", content.text)
                resultIntent.putExtras(bundle)
                setResult(RESULT_OK, resultIntent)
                finish();
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