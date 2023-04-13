package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.ailiwean.core.helper.QRCodeEncoder
import com.apkfuns.logutils.LogUtils
import com.ewivt.redline.abtqr.ABTQrCodeDataSecurity
import com.ewivt.redline.abtqr.data.ABTQrCodeData
import com.ewivt.redline.basecode.Base64
import com.ewivt.redline.datasecurity.DataSignature
import com.ewivt.redline.kmsutils.QrImageUtil
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
 *@Copyright:(C)2022 . All rights reserved.
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
        et_yyyymmddHHMMSSmmm.setText(TimeUtils.getTimeGMTNow() + "000")

        btn_fast_input_time.setOnClickListener {
//            20220309202000000
            et_yyyymmddHHMMSSmmm.setText(TimeUtils.getTimeGMTNow() + "000")
        }

        btn_create_qr_new.setOnClickListener {
            createQR()
        }


        btn_create_qr.setOnClickListener {
            var yyyymmddHHMMSSmmm = et_yyyymmddHHMMSSmmm.text.toString()
            var fare_code = et_fare_code.text.toString()
            var traveler_num = et_traveler_num.text.toString()
            var smartType = et_smart_type.text.toString()
            var stationCode = et_station_code.text.toString()
            var jierudianleixing = et_jierudianleixing.text.toString()

            if (yyyymmddHHMMSSmmm.isEmpty() || fare_code.isEmpty() || traveler_num.isEmpty()) {
                shortToast("请输入内容")
                return@setOnClickListener
            }

            hideSoftInput(mContext, et_yyyymmddHHMMSSmmm)
            val key = keyList[spinner_key.selectedIndex]
            var keyNum = spinner_key.selectedIndex + 1
            LogUtils.e("选择秘钥:$key；秘钥版本号:$keyNum")
            var content =
                yyyymmddHHMMSSmmm + "100000000100000000"+jierudianleixing+stationCode+"10000000001000000000"+smartType+"11" + fare_code + traveler_num + "0000"
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
                    LogUtils.e("生成二维码出错," + it.message)
                }
        }
    }

    private fun createQR(){
        var qrCodeData = ABTQrCodeData()
        qrCodeData.operatorCode = "022"
        qrCodeData.publicKeyVersion = "1"
        qrCodeData.inspectionReferenceVersion = "1234"
        qrCodeData.validation24datetime = "20221111202456789"
        qrCodeData.longitude = "034333333"
        qrCodeData.latitude = "035333333"
        qrCodeData.tripAccessPointType = "1"
        qrCodeData.tripAccessPointStationNumber = "12345678"
        qrCodeData.passengerAcountNumber = "99999999999999999999"
        qrCodeData.smartIdentifierType = "1"
        qrCodeData.passengerLanguage = "1"
        qrCodeData.travelFareCode = "201"
        qrCodeData.passengerNumber = "2"
        qrCodeData.passengerFirstProfile = "00"
        qrCodeData.passengerSecondProfile = "33"
        qrCodeData.signData = "000000000000000000"
        val dd: Map<Int, String> = ABTQrCodeDataSecurity.generatorKey()
        val privateKeys = HashMap<String, String?>()
        val publicKeys = HashMap<String, String?>()
        privateKeys["1"] = dd[1]
        publicKeys["1"] = dd[0]

//        privateKeys.put("1",PrivateKey);
//        publicKeys.put("1", PublicKey);


//        privateKeys.put("1",PrivateKey);
//        publicKeys.put("1", PublicKey);
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

//            LogUtils.e("qrData=$qrData;length="+qrData.length)
            val data: ByteArray = Base64.decodeBase64(qrData)
//            LogUtils.e("qrData parse data length :" + data.size)
//            val bufferedImage = QrImageUtil.generateABTQRCodeImage(data, 350, 350)

//            if (bufferedImage != null) {
//                val output = File("d:\\abtqr_code_write_" + System.currentTimeMillis() + ".png")
//                ImageIO.write(bufferedImage, "png", output)
//            } else {
//            }
//            val sqrData: ABTQrCodeData = abtQrCodeDataSecurity.decryptByPubKey(qrData)
//            log.debug("sqrData=$sqrData")
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