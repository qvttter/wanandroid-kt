package com.li.mykotlinapp.test

import android.content.Context
import android.content.Intent
import com.apkfuns.logutils.LogUtils
import com.ewivt.redline.datasecurity.QrCodeDataSecurityDecrypt
import com.ewivt.redline.exception.KmsException
import com.ewivt.redline.qrcodedata.QrCodeDataLocal
import com.google.zxing.Result
import com.google.zxing.ResultMetadataType
import com.king.zxing.CaptureActivity
import com.king.zxing.DecodeConfig
import com.king.zxing.DecodeFormatManager
import com.king.zxing.analyze.MultiFormatAnalyzer
import com.li.mykotlinapp.R
import com.li.mykotlinapp.util.ToastUtil

/************************************************************************
 *@Project: ipu
 *@Package_Name: com.easyway.ipu.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/2/27
 *@Copyright:(C)2023 . All rights reserved.
 *************************************************************************/
class TestScanLiteActivity : CaptureActivity() {

    var qrCodeDataSecurityDecrypt: QrCodeDataSecurityDecrypt? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_test_scan_lite
    }

    override fun initUI() {
        super.initUI()

        var map = hashMapOf(
            "1" to "MDwwDQYJKoZIhvcNAQEBBQADKwAwKAIhALVSb4Khc06GLLGqw8+xOGfkF+Naae/0eBNeDKtdrcAFAgMBAAE=",
            "2" to "MDwwDQYJKoZIhvcNAQEBBQADKwAwKAIhAJkZGz5ZiTkqr4wtYXW9xQwH/8Rgl6qr0gAyMGaZdeWdAgMBAAE=",
            "6" to "MDwwDQYJKoZIhvcNAQEBBQADKwAwKAIhAPqk+E9SIX+iu2JN4iTTYo7ZeYcN6Nt5pbh+znz7312VAgMBAAE=",
            "8" to "MDwwDQYJKoZIhvcNAQEBBQADKwAwKAIhAMItebcqbUZONIUI7jqFYmevQsdpYYNCEONfWYlgS03rAgMBAAE=",
            "9" to "MDwwDQYJKoZIhvcNAQEBBQADKwAwKAIhAMEjMGoONGrk6TJlcaH15iPmemIWzARgdFRAmNOof8xzAgMBAAE=",
            "12" to "MDwwDQYJKoZIhvcNAQEBBQADKwAwKAIhAJY+4pVoa+kFwLJygaGWUbcQ/9gY/bYB1sa1Iosw+/uBAgMBAAE="


        )
        qrCodeDataSecurityDecrypt = QrCodeDataSecurityDecrypt(map)
    }

    override fun initCameraScan() {
        super.initCameraScan()

        //初始化解码配置
        val decodeConfig = DecodeConfig()
        decodeConfig.setHints(DecodeFormatManager.QR_CODE_HINTS) //如果只有识别二维码的需求，这样设置效率会更高，不设置默认为DecodeFormatManager.DEFAULT_HINTS
            .setFullAreaScan(false) //设置是否全区域识别，默认false
            .setAreaRectRatio(0.8f) //设置识别区域比例，默认0.8，设置的比例最终会在预览区域裁剪基于此比例的一个矩形进行扫码识别
            .setAreaRectVerticalOffset(0).areaRectHorizontalOffset =
            0 //设置识别区域水平方向偏移量，默认为0，为0表示居中，可以为负数

        //在启动预览之前，设置分析器，只识别二维码
        cameraScan
            .setVibrate(true) //设置是否震动，默认为false
            .setNeedAutoZoom(true) //二维码太小时可自动缩放，默认为false
            .setAnalyzer(MultiFormatAnalyzer(decodeConfig)) //设置分析器,如果内置实现的一些分析器不满足您的需求，你也可以自定义去实现
    }


    /**
     * 扫码结果回调
     * @param result
     * @return 返回false表示不拦截，将关闭扫码界面并将结果返回给调用界面；
     * 返回true表示拦截，需自己处理逻辑。当isAnalyze为true时，默认会继续分析图像（也就是连扫）。
     * 如果只是想拦截扫码结果回调，并不想继续分析图像（不想连扫），请在拦截扫码逻辑处通过调
     * 用[CameraScan.setAnalyzeImage]，
     * 因为[CameraScan.setAnalyzeImage]方法能动态控制是否继续分析图像。
     */
    override fun onScanResultCallback(result: Result): Boolean {
        /*
         * 因为setAnalyzeImage方法能动态控制是否继续分析图像。
         *
         * 1. 因为分析图像默认为true，如果想支持连扫，返回true即可。
         * 当连扫的处理逻辑比较复杂时，请在处理逻辑前调用getCameraScan().setAnalyzeImage(false)，
         * 来停止分析图像，等逻辑处理完后再调用getCameraScan().setAnalyzeImage(true)来继续分析图像。
         *
         * 2. 如果只是想拦截扫码结果回调自己处理逻辑，但并不想继续分析图像（即不想连扫），可通过
         * 调用getCameraScan().setAnalyzeImage(false)来停止分析图像。
         */
        //扫码结果
        try {
            //2022-04-20 zhouli修改，适配我们的扫码ByteArray结果
            val list =
                result.resultMetadata[ResultMetadataType.BYTE_SEGMENTS] as ArrayList<*>
            val scanResult = list[0] as ByteArray

            //长度33位的是纸质票二维码
            if (scanResult.size == 33) {
                var qrCodeDataLocalDe = decryptQRCode(scanResult)
                if (qrCodeDataLocalDe == null) {
                    ToastUtil.longToast(this, "qrCodeDataLocalDe == null")
                } else {
                    ToastUtil.longToast(
                        this,
                        "qrCodeDataLocalDe.ticketNumber:" + qrCodeDataLocalDe.ticketNumber
                    )

                }
            } else {
                ToastUtil.longToast(this, "scanResult.size:" + scanResult.size)
            }

        } catch (e: Exception) {
            LogUtils.e("onScanQRCodeByteArraySuccess,error:" + e.message)
        }


        return super.onScanResultCallback(result)

    }

    /**
     * 二维码数据解密
     */
    fun decryptQRCode(scanResult: ByteArray): QrCodeDataLocal? {
        var qrCodeDataSecurityDecrypt = qrCodeDataSecurityDecrypt

        var result: QrCodeDataLocal? = null
        try {
            result = qrCodeDataSecurityDecrypt?.decryptQrData(
                scanResult,
                qrCodeDataSecurityDecrypt.publicKey
            )
            LogUtils.e("二维码数据解密,result:$result")

        } catch (e: KmsException) {
            LogUtils.e("decryptQRCodeError:" + e.messageCN)
            return null
        }

        return result
    }


    companion object {
        fun start(content: Context) {
            val intent = Intent(content, TestScanLiteActivity().javaClass)
            content.startActivity(intent)
        }
    }

}