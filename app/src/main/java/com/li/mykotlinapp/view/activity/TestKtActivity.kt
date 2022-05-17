package com.li.mykotlinapp.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apkfuns.logutils.LogUtils
import com.gengqiquan.result.startActivityWithResult
import com.jeremyliao.liveeventbus.LiveEventBus
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.TestButtonListAdapter
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.bean.bus.LocationBus
import com.li.mykotlinapp.common.Constants
import com.li.mykotlinapp.test.*
import com.li.mykotlinapp.test.bluetoothPrinter.BluetoothPrintActivity
import com.tbruyelle.rxpermissions2.RxPermissions
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
    lateinit var adapter: TestButtonListAdapter
    lateinit var btnList: MutableList<String>

    override fun getLayout(): Int {
        return R.layout.activity_test
    }

    override fun initData() {
        LiveEventBus.get(Constants.BUS_LOCATION)
            .post(LocationBus("", ""))

        btnList = ArrayList()
        btnList.add("objectBox")
        btnList.add("excel")
        btnList.add("scan")
        btnList.add("dialog")
        btnList.add("compose")
        btnList.add("commonView")
        btnList.add("imgShake")
        btnList.add("MainActivity")
        btnList.add("bluetoothPrinter")
        btnList.add("TextToSpeech")
        btnList.add("ScanActivity")
        btnList.add("OpenGLActivity")
        btnList.add("MQTT")

        adapter = TestButtonListAdapter(btnList)
        rcv_button.adapter = adapter
        rcv_button.layoutManager = LinearLayoutManager(mContext)
        rcv_button.addItemDecoration(DividerItemDecoration(mContext,LinearLayoutManager.VERTICAL))
        adapter.data = btnList

        initEvent()
        getPermission()
    }

    private fun getPermission() {
        RxPermissions(this)
            .request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA
//                Manifest.permission.BLUETOOTH_SCAN
            ).subscribe(
                { granted: Boolean ->
                    if (granted) {
//                        MaterialDialog(mContext).show {
//                            message (text= "请允许所有权限")
//                            positiveButton(R.string.str_confirm){
//                                getPermission()
//                            }
//                            negativeButton(text = "取消"){
//                                finish()
//                            }
//                        }
                    } else {
                        finish()
                    }
                },
                { t: Throwable? ->
                    finish()
                }
            )
    }


    private fun initEvent() {
        tv_text.setOnClickListener {
            tv_text.text = ""
        }
        adapter.setOnItemClickListener { adapter, view, position ->
            val bean = adapter.getItem(position) as String
            when (bean) {
                "objectBox" -> {
                    ObjectActivity.start(mContext)
                }
                "excel" -> {
                    ExcelActivity.start(mContext)
                }
                "MainActivity" ->{
                    MainActivity.start(mContext)
                }
                "scan" -> {
                    startActivityWithResult<ScanActivity>()
                        .filter { it.isOK }
                        .subscribe({ result ->
                            val scanResult = result.data.getStringExtra("result")
                            LogUtils.e("扫码结果:" + result.data)
                            tv_text.text = "扫码结果:$scanResult"
                        }) { e ->
                            e.printStackTrace()
                            tv_text.text = "扫码异常:" + e.message
                            LogUtils.e("扫码异常:" + e.message)
                        }
                }
                "dialog" -> {
                    Observable.timer(5, TimeUnit.SECONDS)
                        .doOnSubscribe { showLoading("") }
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { t ->
                            hideLoading()
                        }
                }
                "compose" -> {
                    TestComposeActivity.start(mContext)
                }
                "commonView" -> {
                    TestCommonViewActivity.start(mContext)
                }
                "imgShake" -> {
                    var anim = AnimationUtils.loadAnimation(this, R.anim.shake)
                    iv_logo.startAnimation(anim)
                }
                "bluetoothPrinter" ->{
                    BluetoothPrintActivity.start(mContext)
                }
                "TextToSpeech" ->{
                    TextToSpeechActivity.start(mContext)
                }
                "ScanActivity"->{
                    ScanActivity.start(mContext)
                }
                "OpenGLActivity" ->{
                    OpenGLActivity.start(mContext)
                }
                "MQTT"->{
                    MQTTActivity.start(mContext)
                }
            }
        }

//        btn_open_double_app.setOnClickListener {
//            val i = packageManager.getLaunchIntentForPackage("com.easyway.testsecondscreen")
//            startActivity(i)
//        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }

        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    shortToast("ACTION_MANAGE_OVERLAY_PERMISSION权限成功")
                    TestDoubleScreenActivity.start(mContext)

                } else {
                    shortToast("ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝")
                }
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