package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import com.apkfuns.logutils.LogUtils
import com.gengqiquan.result.startActivityWithResult
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.test.TestCommonViewActivity
import com.li.mykotlinapp.test.TestComposeActivity
import com.li.mykotlinapp.widget.TestDialog
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_test.*
import java.util.concurrent.TimeUnit

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        when (requestCode) {
            REQUEST_CODE_SCAN_DEFAULT_MODE -> {
                val hmsScan: HmsScan? = data.getParcelableExtra(ScanUtil.RESULT) // 获取扫码结果 ScanUtil.RESULT
                if (!TextUtils.isEmpty(hmsScan?.getOriginalValue())) {
                    var result = hmsScan?.getOriginalValue()
                    tv_text.text = result

                    result!!.toByteArray()
                }
            }
        }
    }

    private fun initEvent() {
        tv_text.setOnClickListener {
            tv_text.text = ""
        }
        btn_scan_kit.setOnClickListener {
            val options =
                HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create()
            ScanUtil.startScan(
                this, REQUEST_CODE_SCAN_DEFAULT_MODE,
                options
            )
        }

        btn_compose.setOnClickListener { TestComposeActivity.start(mContext) }

        btn_dialog.setOnClickListener {
            Observable.timer(5, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t ->
                    var dialog = TestDialog(mContext)
                    dialog.show()
                }
        }

        btn_object_box.setOnClickListener {
            ObjectActivity.start(mContext)
        }
        btn_hellosmartcard.setOnClickListener {
        }

        btn_OmapiActivity.setOnClickListener {
        }
        btn_mqtt.setOnClickListener {
        }
        btn_excel.setOnClickListener {
            ExcelActivity.start(mContext)
        }
        btn_common.setOnClickListener {
            TestCommonViewActivity.start(mContext)
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
        const val REQUEST_CODE_SCAN_DEFAULT_MODE = 1

        fun start(content: Context) {
            val intent = Intent(content, TestKtActivity().javaClass)
            content.startActivity(intent)
        }
    }
}