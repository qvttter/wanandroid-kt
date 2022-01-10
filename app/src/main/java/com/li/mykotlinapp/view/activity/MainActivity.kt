package com.li.mykotlinapp.view.activity

import android.Manifest
import android.content.*
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentTransaction
import com.li.mykotlinapp.widget.helper.BottomNavigationViewHelper
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.google.android.material.navigation.NavigationView
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.util.FloatTool
import com.li.mykotlinapp.view.fragment.IndexFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import com.youth.banner.util.LogUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*


class MainActivity : BaseActivity() {
    private val pkgName = "io.ionic.starter"
    private val pkgNameClass = "io.ionic.starter.MainActivity"

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
                        Manifest.permission.CAMERA
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

    private fun initView(){
        listenerUpdate()
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
