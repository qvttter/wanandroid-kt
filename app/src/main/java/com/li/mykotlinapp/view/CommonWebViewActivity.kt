package com.li.mykotlinapp.view

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.common.Constants
import kotlinx.android.synthetic.main.activity_web_common.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class CommonWebViewActivity : BaseActivity() {
    lateinit var mWebView: AgentWeb

    override fun getLayout(): Int {
        return R.layout.activity_web_common

    }

    override fun initData() {
        var url = intent.getStringExtra(Constants.URL)
        mWebView = AgentWeb.with(this)
                .setAgentWebParent(container_framelayout as FrameLayout, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url)
    }

    override fun onPause() {
        mWebView.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mWebView.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mWebView.webLifeCycle.onDestroy()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mWebView.handleKeyEvent(keyCode, event)) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    companion object {
        fun start(content: Context, url: String) {
            val intent = Intent(content, CommonWebViewActivity().javaClass)
            intent.putExtra(Constants.URL, url)
            content.startActivity(intent)
        }
    }
}