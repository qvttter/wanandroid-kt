package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseActivity
import com.li.mykotlinapp.common.Constants
import kotlinx.android.synthetic.main.activity_web_common.*
import kotlinx.android.synthetic.main.include_toolbar.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23
 
 *************************************************************************/
class CommonWebViewActivity : BaseActivity() {
    lateinit var mWebView: AgentWeb

    override fun getLayout(): Int {
        return R.layout.activity_web_common

    }

    override fun initData() {
        var url = intent.getStringExtra(Constants.URL)
        var title = intent.getStringExtra(Constants.WEB_VIEW_TITLE) ?:""
        initToolBar(title)
        mWebView = AgentWeb.with(this)
                .setAgentWebParent(container_framelayout as FrameLayout, LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .createAgentWeb()
                .ready()
                .go(url)
    }

    private val mWebChromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            setToolBarTitle(title)
        }
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
        fun start(context: Context, url: String,title: String) {
            val intent = Intent(context, CommonWebViewActivity().javaClass)
            intent.putExtra(Constants.URL, url)
            intent.putExtra(Constants.WEB_VIEW_TITLE, title)
            context.startActivity(intent)
        }
    }
}