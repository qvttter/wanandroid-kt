package com.li.mykotlinapp.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.li.mykotlinapp.util.ToastUtil
import kotlinx.android.synthetic.main.include_toolbar.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/9/30
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
abstract class BaseActivity : AppCompatActivity() {
    protected lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        mContext = this
        initData()
    }

    abstract fun getLayout(): Int
    abstract fun initData()

    /**
     * 处理通用toolbar，需要在布局里<include layout="@layout/common_toolbar" />
     * 暂时只处理带返回按钮的。
     */
    fun initToolBar(title: String) {
        tv_title.text = title
        tool_bar.setNavigationOnClickListener { finish() }
    }

    fun setToolBarTitle(title: String) {
        tv_title.text = title
    }

    fun shortToast(msg: String?) {
        ToastUtil.shortToast(mContext, msg)
    }

    fun longToast(msg: String) {
        ToastUtil.longToast(mContext, msg)
    }
}