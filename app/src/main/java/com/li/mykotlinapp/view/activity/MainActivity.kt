package com.li.mykotlinapp.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
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
import com.li.mykotlinapp.test.hellosmartcardActivity
import com.li.mykotlinapp.view.fragment.IndexFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val FRAGMENT_HOME = 0x01
    private val FRAGMENT_KNOWLEDGE = 0x02
    private val FRAGMENT_NAVIGATION = 0x03
    private val FRAGMENT_PROJECT = 0x04
    private val FRAGMENT_WECHAT = 0x05
    private var nav_username: TextView? = null
    private var nav_userhead: ImageView? = null

    private var indexFragment: IndexFragment? = null

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
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
        tool_bar.run {
            title = getString(R.string.home)
            setSupportActionBar(this)
        }

        drawer_layout.run {
            var toggle = ActionBarDrawerToggle(
                    this@MainActivity,
                    this,
                    tool_bar
                    , R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }

        bottom_navigation.run {
            // 以前使用 BottomNavigationViewHelper.disableShiftMode(this) 方法来设置底部图标和字体都显示并去掉点击动画
            // 升级到 28.0.0 之后，官方重构了 BottomNavigationView ，目前可以使用 labelVisibilityMode = 1 来替代
            BottomNavigationViewHelper.disableShiftMode(this)
//            labelVisibilityMode = 1
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
            setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_home -> {
                        showFragment(FRAGMENT_HOME)
                        true
                    }
                    R.id.action_knowledge_system -> {
                        showFragment(FRAGMENT_KNOWLEDGE)
                        true
                    }
                    R.id.action_navigation -> {
                        showFragment(FRAGMENT_NAVIGATION)
                        true
                    }
                    R.id.action_project -> {
                        showFragment(FRAGMENT_PROJECT)
                        true
                    }
                    R.id.action_wechat -> {
                        showFragment(FRAGMENT_WECHAT)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }

        showFragment(FRAGMENT_HOME)

        nav_view.run {
            nav_username = getHeaderView(0).findViewById(R.id.tv_username)
            nav_username?.setOnClickListener { LoginActivity.start(mContext) }
            nav_userhead = getHeaderView(0).findViewById(R.id.iv_head)
            nav_userhead?.setOnClickListener { LoginActivity.start(mContext) }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_collect -> {
            }
            R.id.nav_todo -> {

            }
            R.id.nav_night_mode -> {

            }
            R.id.nav_setting -> {

            }
            R.id.nav_about_us -> {

            }
            R.id.nav_test ->{
                TestKtActivity.start(mContext)
            }
            R.id.nav_logout -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * 展示Fragment
     * @param index
     */
    private fun showFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
//        mIndex = index
        when (index) {
            FRAGMENT_HOME // 首页
            -> {
                tool_bar.title = "首页"
                if (indexFragment == null) {
                    indexFragment = IndexFragment.newInstance()
                    transaction.add(R.id.container, indexFragment!!, "home")
                } else {
                    transaction.show(indexFragment!!)
                }
            }
//            FRAGMENT_KNOWLEDGE // 知识体系
//            -> {
//                toolbar.title = getString(R.string.knowledge_system)
//                if (mKnowledgeTreeFragment == null) {
//                    mKnowledgeTreeFragment = KnowledgeTreeFragment.getInstance()
//                    transaction.add(R.id.container, mKnowledgeTreeFragment!!, "knowledge")
//                } else {
//                    transaction.show(mKnowledgeTreeFragment!!)
//                }
//            }
//            FRAGMENT_NAVIGATION // 导航
//            -> {
//                toolbar.title = getString(R.string.navigation)
//                if (mNavigationFragment == null) {
//                    mNavigationFragment = NavigationFragment.getInstance()
//                    transaction.add(R.id.container, mNavigationFragment!!, "navigation")
//                } else {
//                    transaction.show(mNavigationFragment!!)
//                }
//            }
//            FRAGMENT_PROJECT // 项目
//            -> {
//                toolbar.title = getString(R.string.project)
//                if (mProjectFragment == null) {
//                    mProjectFragment = ProjectFragment.getInstance()
//                    transaction.add(R.id.container, mProjectFragment!!, "project")
//                } else {
//                    transaction.show(mProjectFragment!!)
//                }
//            }
//            FRAGMENT_WECHAT // 公众号
//            -> {
//                toolbar.title = getString(R.string.wechat)
//                if (mWeChatFragment == null) {
//                    mWeChatFragment = WeChatFragment.getInstance()
//                    transaction.add(R.id.container, mWeChatFragment!!, "wechat")
//                } else {
//                    transaction.show(mWeChatFragment!!)
//                }
//            }
        }
        transaction.commit()
    }

    /**
     * 隐藏所有的Fragment
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        indexFragment?.let { transaction.hide(it) }
//        mKnowledgeTreeFragment?.let { transaction.hide(it) }
//        mNavigationFragment?.let { transaction.hide(it) }
//        mProjectFragment?.let { transaction.hide(it) }
//        mWeChatFragment?.let { transaction.hide(it) }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, MainActivity().javaClass)
            content.startActivity(intent)
        }
    }
}
