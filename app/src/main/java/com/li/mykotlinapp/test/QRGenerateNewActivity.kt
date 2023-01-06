package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ailiwean.core.helper.QRCodeEncoder
import com.apkfuns.logutils.LogUtils
import com.ewivt.redline.abtqr.ABTQrCodeDataSecurity
import com.ewivt.redline.abtqr.data.ABTQrCodeData
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.util.DensityUtil
import com.li.mykotlinapp.util.RxUtil
import com.li.mykotlinapp.util.TimeUtils
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_qr_generate_new.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/11/22
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class QRGenerateNewActivity : BaseActivity() {
    private var privateKeyString =
        "base64=MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQg0bk9zB40S/xhFneIvK+rXm7oOk3WcGrb82OACYYqMR+gCgYIKoZIzj0DAQehRANCAASGQkxMZ/ZhuSKvmaWKv4S9SpYKBpdosoS2F4SPsjkT3EqEL3pRAPBTVZ5q6N2inB5lJE2RpQdIvuJoePvSRYfd"
    private var publicKeyString =
        "base64=MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEhkJMTGf2Ybkir5mlir+EvUqWCgaXaLKEtheEj7I5E9xKhC96UQDwU1WeaujdopweZSRNkaUHSL7iaHj70kWH3Q=="


    override fun getLayout(): Int {
        return R.layout.activity_qr_generate_new
    }

    override fun initData() {
//        spinner_key.attachDataSource(keyNameList)
        et_yyyymmddHHMMSSmmm.setText(TimeUtils.getTimeGMTNow() + "000")

        btn_create_qr.setOnClickListener {
            createQR()
        }

        btn_create_qr.setOnClickListener {
            createQR()
        }
    }

    private fun createQR() {
        var qrCodeData = ABTQrCodeData()
        qrCodeData.operatorCode = et_operator_code.text.toString()
        qrCodeData.publicKeyVersion = "8"
        qrCodeData.inspectionReferenceVersion = "0023"
        qrCodeData.validation24datetime = et_yyyymmddHHMMSSmmm.text.toString()
        qrCodeData.longitude = "034983092"
        qrCodeData.latitude = "032805341"
        qrCodeData.tripAccessPointType = "5"
        qrCodeData.tripAccessPointStationNumber =
            et_station_code.text.toString() + et_operator_code.text.toString()
        qrCodeData.passengerAcountNumber = "30123246947085109132"
        qrCodeData.smartIdentifierType = "2"
        qrCodeData.passengerLanguage = "1"
        qrCodeData.validationModelDepature = "1"
        qrCodeData.validationModelDestination = "1"
        qrCodeData.travelFareCode = et_fare_code.text.toString()
        qrCodeData.passengerNumber = et_traveler_num.text.toString()
        qrCodeData.passengerFirstProfile = et_profile1.text.toString()
        qrCodeData.passengerSecondProfile = et_profile2.text.toString()
        qrCodeData.signData = et_sign_data.text.toString()
        val dd: Map<Int, String> = ABTQrCodeDataSecurity.generatorKey()
        val privateKeys = HashMap<String, String?>()
        val publicKeys = HashMap<String, String?>()
        privateKeys["8"] = dd[1]
        publicKeys["8"] = dd[0]

        val abtQrCodeDataSecurity = ABTQrCodeDataSecurity(privateKeys, publicKeys)
        for (i in 0..0) {
            val qrData: String = abtQrCodeDataSecurity.encryptQrData(qrCodeData)
            Observable.just(
                QRCodeEncoder.syncEncodeQRCode(
                    qrData,
                    DensityUtil.dip2px(mContext, 200f)
                )
            )
                .compose(RxUtil.trans_io_main())
                .subscribe({
                    iv_qr.setImageBitmap(it)
                }
                ) {
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
            val intent = Intent(content, QRGenerateNewActivity().javaClass)
            content.startActivity(intent)
        }
    }
}