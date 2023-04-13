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
import com.li.mykotlinapp.view.dialog.CommonImgDialog
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_qr_generate_new.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/11/22
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
class QRGenerateNewActivity : BaseActivity() {
    private val publicKeyString =
        "MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAE/6RkYKwuConYPM1ByYyQuz0tcN6s6npDmVpEm0qXzMwf5RsqTPWMr6QqxDU1LlnHGR8hP+QHKqm1a0pRq3D7Xg=="
    private val privateKeyString =
        "MIGTAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBHkwdwIBAQQgkBeXUJrzcbl5A7BVipYyIE/vB4LrYx6dWBtgaWOpWl6gCgYIKoZIzj0DAQehRANCAAT/pGRgrC4Kidg8zUHJjJC7PS1w3qzqekOZWkSbSpfMzB/lGypM9YyvpCrENTUuWccZHyE/5AcqqbVrSlGrcPte"


    override fun getLayout(): Int {
        return R.layout.activity_qr_generate_new
    }

    override fun initData() {
//        spinner_key.attachDataSource(keyNameList)
        et_yyyymmddHHMMSSmmm.setText(TimeUtils.getTimeGMTNow() + "000")
        var a = 33
        LogUtils.e("aaa:" +(a/3))

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
        qrCodeData.publicKeyVersion = et_public_key_version.text.toString()
        qrCodeData.inspectionReferenceVersion = et_inspection_reference_version.text.toString()
        qrCodeData.validation24datetime = et_yyyymmddHHMMSSmmm.text.toString()
//        qrCodeData.validation24datetime = "20221220050139926"
        qrCodeData.longitude = et_longitude.text.toString()
        qrCodeData.latitude = et_latitude.text.toString()
        qrCodeData.tripAccessPointType = et_tripAccess_point_type.text.toString()
//        qrCodeData.tripAccessPointStationNumber =
//            et_station_code.text.toString() + et_operator_code.text.toString()
        qrCodeData.tripAccessPointStationNumber = et_trip_access_point_station_number.text.toString()
        qrCodeData.passengerAcountNumber = et_passenger_account_number.text.toString()
        qrCodeData.smartIdentifierType = et_smart_identifier_type.text.toString()
        qrCodeData.passengerLanguage = et_passenger_language.text.toString()
        qrCodeData.validationModelDepature = et_validation_model_depature.text.toString()
        qrCodeData.validationModelDestination = et_validation_model_destination.text.toString()
        qrCodeData.travelFareCode = et_fare_code.text.toString()
        qrCodeData.passengerNumber = et_traveler_num.text.toString()
        qrCodeData.passengerFirstProfile = et_profile1.text.toString()
        qrCodeData.passengerSecondProfile = et_profile2.text.toString()
        qrCodeData.signData = null
        val privateKeys = HashMap<String, String?>()
        val publicKeys = HashMap<String, String?>()
        privateKeys[qrCodeData.publicKeyVersion] = privateKeyString
        publicKeys[qrCodeData.publicKeyVersion] = publicKeyString


        val abtQrCodeDataSecurity = ABTQrCodeDataSecurity(privateKeys, publicKeys)
        val qrData: String = abtQrCodeDataSecurity.encryptQrData(qrCodeData)
        CommonImgDialog.newInstance(qrData = qrData).show(supportFragmentManager, "CommonImgDialog")
//            Observable.just(
//                QRCodeEncoder.syncEncodeQRCode(
//                    qrData,
//                    DensityUtil.dip2px(mContext, 200f)
//                )
//            )
//                .compose(RxUtil.trans_io_main())
//                .subscribe({
//                    iv_qr.setImageBitmap(it)
//                }
//                ) {
//                    LogUtils.e("生成二维码出错," + it.message)
//                }
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