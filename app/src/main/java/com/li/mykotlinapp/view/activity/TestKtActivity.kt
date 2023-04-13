package com.li.mykotlinapp.view.activity

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.res.Configuration
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apkfuns.logutils.LogUtils
import com.gengqiquan.result.startActivityWithResult
import com.google.gson.Gson
import com.jeremyliao.liveeventbus.LiveEventBus
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.TestButtonListAdapter
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.test.*
import com.li.mykotlinapp.test.bluetoothPrinter.BluetoothPrintActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/11/5

 *************************************************************************/
class TestKtActivity : BaseActivity() {
    private val pkgName = "com.ewivt.p92.upgradeguardianapp"
    private val pkgNameClass = "com.ewivt.p92.upgradeguardianapp.view.UpdateActivity"

    lateinit var adapter: TestButtonListAdapter
    lateinit var btnList: MutableList<String>
    private var job: Job? = null

    var a: Any? = null
    lateinit var list: MutableList<String>

    override fun getLayout(): Int {
        return R.layout.activity_test
    }

    override fun initData() {
//        flow {
//            for (i in 60 downTo 0) {
//                emit(i)
//                delay(1000)
//            }
//        }
//            .flowOn(Dispatchers.Default)
//            .onCompletion { tv_text.append("倒计时结束\n") }
//            .onEach{tv_text.append( "倒计时:${it}s\n")}
//            .flowOn(Dispatchers.Main)
//            .launchIn(lifecycleScope)

        when (a) {
            null -> {
                LogUtils.e("a is null ")
            }
            is String -> {
                LogUtils.e("a is string ")

            }
            "3" -> {
                LogUtils.e("a is 3 ")
            }
        }

        list = ArrayList()
        list.add("abc")
        list.add("efg")
        list.add("123")
        list.add("vvv")
        list.add("qqq")

        list.filter { it.startsWith("a") }
            .sortedBy { it }





        btnList = ArrayList()
        btnList.add("ABTQR")
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
        btnList.add("DoubleScreen")
        btnList.add("TestUpdate")
        btnList.add("dispatchEvent")
        btnList.add("scope")
        btnList.add("xiaodaoda")
        btnList.add("dadaoxiao")
        btnList.add("TestRxjava")
        btnList.add("TestRecyclerActivity")
        btnList.add("TestScanLiteActivity")

        var list = Gson().toJson(btnList)
        LogUtils.e("list:$list")


        adapter = TestButtonListAdapter(btnList)
        rcv_button.adapter = adapter
        rcv_button.layoutManager = LinearLayoutManager(mContext)
        rcv_button.addItemDecoration(DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL))
        adapter.data = btnList

        spinner_test.attachDataSource(btnList)

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
                "ABTQR" -> {
                    QRGenerateNewActivity.start(mContext)
                }
                "objectBox" -> {
                    ObjectActivity.start(mContext)
                }
                "excel" -> {
                    ExcelActivity.start(mContext)
                }
                "MainActivity" -> {
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
                "bluetoothPrinter" -> {
                    BluetoothPrintActivity.start(mContext)
                }
                "TextToSpeech" -> {
                    TextToSpeechActivity.start(mContext)
                }
                "ScanActivity" -> {
                    ScanActivity.start(mContext)
                }
                "OpenGLActivity" -> {
                    OpenGLActivity.start(mContext)
                }
                "DoubleScreen" -> {
//                    val i = packageManager.getLaunchIntentForPackage("com.easyway.testsecondscreen")
//                    startActivity(i)
                    TestDoubleScreenActivity.start(mContext)
                }
                "TestUpdate" -> {
                    LiveEventBus.get<String>("attributes")
                        .postAcrossApp("hahaha")
//                    RxTools.openApp(pkgName, pkgNameClass)
                    LogUtils.e("向辅助app发送消息")

                    var intent =
                        packageManager.getLaunchIntentForPackage(
                            pkgName
                        )
                    if (intent != null) {
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        LogUtils.e("startActivity," + intent.toString())
                    }

                    exitProcess(0)
                }
                "dispatchEvent" -> {
                    DispatchEventActivity.start(mContext)
                }
                "scope" -> {
                    //作用域
                    ScopeActivity.start(mContext)
                }
                "xiaodaoda" -> {
                    var list = test(intArrayOf(100, 102, 99, 110, 80), 1)
                    var sb = StringBuffer()
                    for (i in list) {
                        sb.append(i)
                        sb.append(";")
                    }
                    tv_text.text = sb.toString()

                }
                "dadaoxiao" -> {
                    var list = test(intArrayOf(100, 102, 99, 110, 80), 2)
                    var sb = StringBuffer()
                    for (i in list) {
                        sb.append(i)
                        sb.append(";")
                    }
                    tv_text.text = sb.toString()
                }
                "TestRxjava" -> {
                    TestRxjava.start(mContext)
                }
                "TestRecyclerActivity" -> {
                    TestRecyclerActivity.start(mContext)
                }
                "TestScanLiteActivity" -> {
                    TestScanLiteActivity.start(mContext)

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }

        if (requestCode == 100) {
            if (Settings.canDrawOverlays(this)) {
                LogUtils.e("ACTION_MANAGE_OVERLAY_PERMISSION权限成功")
                shortToast("ACTION_MANAGE_OVERLAY_PERMISSION权限成功")
                TestDoubleScreenActivity.start(mContext)

            } else {
                shortToast("ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝")
                LogUtils.e("ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝")
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.e("onCreate")
    }

    override fun onStart() {
        super.onStart()
        LogUtils.e("onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.e("onResume")
    }

    override fun onPause() {
        LogUtils.e("onPause")
        super.onPause()
    }

    override fun onStop() {
        LogUtils.e("onStop")
        super.onStop()
    }

    override fun onDestroy() {
        LogUtils.e("onDestroy")
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LogUtils.e("onConfigurationChanged")
    }


    //type=2 从大到小，1从小到大
    private fun test(list: IntArray, type: Int): IntArray {
        var temp: Int

        //判断size
        if (list.size < 2) {
            return list
        }

        if (type == 1) {
            for (i in list.indices) {
                for (j in 0 until list.size - i - 1) {
                    if (list[j] > list[j + 1]) {
                        temp = list[j + 1]
                        list[j + 1] = list[j]
                        list[j] = temp
                    }
                }
            }
            return list
        }

        if (type == 2) {
            for (i in list.indices) {
                for (j in 0 until list.size - i - 1) {
                    if (list[j] < list[j + 1]) {
                        temp = list[j + 1]
                        list[j + 1] = list[j]
                        list[j] = temp
                    }
                }
            }
            return list
        }
        return list
    }

}