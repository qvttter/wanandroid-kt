package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity


/************************************************************************
 *@Project: yiselie
 *@Package_Name: com.easyway.ipu.utils.sacn
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/19
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
//QRCodeView.Delegate
class BGAQRCodeActivity : BaseActivity() {


    override fun getLayout(): Int {
        return R.layout.activity_bga_qr_code
    }

    override fun initData() {
//        zxingview.setDelegate(this)
//        zxingview.startCamera()
//        zxingview.startSpotAndShowRect()

    }

    override fun onStop() {
//        zxingview.stopCamera()
        super.onStop()
    }

    override fun onDestroy() {
//        zxingview.onDestroy()
        super.onDestroy()
    }


    companion object {
            fun start(content: Context){
                val intent = Intent(content, BGAQRCodeActivity().javaClass)
                content.startActivity(intent)
            }
        }

//    override fun onScanQRCodeSuccess(result: String?) {
//        LogUtils.e("扫码结果："+result)
//    }
//
//    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
//    }
//
//    override fun onScanQRCodeOpenCameraError() {
//    }
}