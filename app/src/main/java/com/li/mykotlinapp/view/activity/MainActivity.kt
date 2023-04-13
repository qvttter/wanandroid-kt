package com.li.mykotlinapp.view.activity

import android.Manifest
import android.content.*
import androidx.lifecycle.Observer
import com.apkfuns.logutils.LogUtils
import com.jeremyliao.liveeventbus.LiveEventBus
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.util.FloatTool
import com.li.mykotlinapp.util.RxTools
import com.tbruyelle.rxpermissions2.RxPermissions


class MainActivity : BaseActivity() {
    private val pkgName = "com.easyway.pdp"
    private val pkgNameClass = "com.easyway.pdp.MainActivity"

    override fun getLayout(): Int {
        return R.layout.activity_main
    }
    override fun initData() {
        getPermission()
        FloatTool.RequestOverlayPermission(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        FloatTool.onActivityResult(requestCode, resultCode, data, this)
    }

    private fun getPermission() {
        RxPermissions(this)
            .request(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
//                Manifest.permission.SYSTEM_ALERT_WINDOW
//                Manifest.permission.BLUETOOTH_SCAN
            ).subscribe(
                { granted: Boolean ->
                    if (granted) {
                        initView()
                    } else {
                        finish()
                    }
                },
                { t: Throwable? ->
                    finish()
                }
            )
    }

    private fun initView() {
//        listenerUpdate()
        LiveEventBus
            .get("attributes", String::class.java)
            .observe(this, Observer {
                LogUtils.e("收到来自PDP的消息："+it)

            })
    }

    private fun listenerUpdate() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED)
        intentFilter.addDataScheme("package")
        registerReceiver(mInstallAppBroadcastReceiver, intentFilter)
    }

    private val mInstallAppBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val packageName = intent.data!!.schemeSpecificPart
            val action = intent.action
            if (action == Intent.ACTION_PACKAGE_REPLACED) {
                LogUtils.e("安装包替换的广播")
                //需要静默升级的app包名
                if (packageName == pkgName) {
                    val componentName = ComponentName(pkgName, pkgNameClass)
                    val intent1 = Intent()
                    intent1.component = componentName
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    LogUtils.e("开始唤醒app")
                    startActivity(intent1)
                }
            }
        }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, MainActivity().javaClass)
            content.startActivity(intent)
        }
    }
}
