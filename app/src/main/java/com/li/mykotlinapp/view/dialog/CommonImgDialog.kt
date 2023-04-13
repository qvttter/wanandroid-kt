package com.li.mykotlinapp.view.dialog

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import com.ailiwean.core.helper.QRCodeEncoder
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseDialog
import com.li.mykotlinapp.util.DensityUtil
import com.li.mykotlinapp.util.RxUtil
import io.reactivex.Observable
import kotlinx.android.synthetic.main.dialog_common_img.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.dialog
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/1/19
 *@Copyright:(C)2023 . All rights reserved.
 *************************************************************************/
class CommonImgDialog : BaseDialog() {

    private var qrData = ""

    override fun getLayout(): Int {
        return R.layout.dialog_common_img
    }

    override fun initData() {
        qrData = arguments?.getString(QR_DATA) ?: ""
        if (qrData.isNotEmpty()) {
            Observable.just(
                QRCodeEncoder.syncEncodeQRCode(
                    qrData,
                    DensityUtil.dip2px(mContext, 200f)
                )
            )
                .compose(RxUtil.trans_io_main())
                .subscribe({
                    iv_common.setImageBitmap(it)
                }
                ) {
                    LogUtils.e("生成二维码出错," + it.message)
                }
        }
    }

    override fun onResume() {
        dialog!!.window!!.setLayout(
            DensityUtil.dip2px(mContext, 350f),
            DensityUtil.dip2px(mContext, 350f)
        )
        super.onResume()
    }

    companion object {
        const val QR_DATA = "qrData"

        fun newInstance(qrData: String): CommonImgDialog {
            var arg = Bundle()
            arg.putString(QR_DATA, qrData)
            var fg = CommonImgDialog()
            fg.arguments = arg
            return fg

        }
    }


}