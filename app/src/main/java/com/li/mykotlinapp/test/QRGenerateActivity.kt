package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ailiwean.core.helper.QRCodeEncoder
import com.apkfuns.logutils.LogUtils
import com.ewivt.redline.datasecurity.DataSignature
import com.ewivt.redline.keymanagement.KeySecurity
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.util.DensityUtil
import com.li.mykotlinapp.util.RxUtil
import com.li.mykotlinapp.util.TimeUtils
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_qr_generate.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/2/15
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class QRGenerateActivity : BaseActivity() {
    private var keyList = listOf(
        "kn9jdKx2MmC8eyEiq4tanQ==",
        "14Ah7m25zgmaznMJwO2h5g==",
        "4hFjGzHuzngVek0XXX97iw==", "7d/eHfo8UUAqAC8VvY/Aiw==", "DUbrib1sU9nwur1hrvJkaA=="
    )
    private var keyNameList = listOf("1号秘钥", "2号秘钥", "3号秘钥", "4号秘钥", "5号秘钥")

    override fun getLayout(): Int {
        return R.layout.activity_qr_generate
    }



    override fun initData() {
        spinner_key.attachDataSource(keyNameList)
        btn_fast_input_time.setOnClickListener {
//            20220309202000000
            et_yyyymmddHHMMSSmmm.setText(TimeUtils.getTimeGMTNow() + "000")
        }

        btn_create_qr.setOnClickListener {
            var yyyymmddHHMMSSmmm = et_yyyymmddHHMMSSmmm.text.toString()
            var fare_code = et_fare_code.text.toString()
            var traveler_num = et_traveler_num.text.toString()

            if (yyyymmddHHMMSSmmm.isEmpty() || fare_code.isEmpty() || traveler_num.isEmpty()) {
                shortToast("请输入内容")
                return@setOnClickListener
            }

            hideSoftInput(mContext, et_yyyymmddHHMMSSmmm)
            val key = keyList[spinner_key.selectedIndex]
            var keyNum = spinner_key.selectedIndex + 1
            LogUtils.e("选择秘钥:$key；秘钥版本号:$keyNum")
            var content =
                yyyymmddHHMMSSmmm + "10000000010000000061111100110000000001000000000211" + fare_code + traveler_num + "0000"
            LogUtils.e("原文:" + content)
//            var cipherText = ""
//            try {
//                cipherText = KeySecurity.encryptSymmetirKey(content, key)
//            } catch (e: Exception) {
//                LogUtils.e("encryptSymmetirKey出错：" + e.message)
//
//            }

            var cipherText =  DataSignature.dataEncryption(key,content)
            var compose = "022" + keyNum + "1000" + cipherText
            LogUtils.e("明文+密文:$compose")
            Observable.just(
                QRCodeEncoder.syncEncodeQRCode(
                    compose,
                    DensityUtil.dip2px(mContext, 200f)
                )
            )
                .compose(RxUtil.trans_io_main())
                .subscribe({
                    iv_qr.setImageBitmap(it)
                }
                ) {
                    shortToast("生成二维码出错," + it.message)
                    LogUtils.e("生成二维码出错," + it.message)
                }
        }
    }

    private fun hideSoftInput(context: Context, view: View) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0) //强制隐藏键盘
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, QRGenerateActivity().javaClass)
            content.startActivity(intent)
        }
    }
}