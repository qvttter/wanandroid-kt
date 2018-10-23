package com.li.mykotlinapp.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.li.mykotlinapp.util.ToastUtil

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

    fun shortToast(msg: String) {
        ToastUtil.shortToast(mContext, msg)
    }

    fun longToast(msg: String) {
        ToastUtil.longToast(mContext, msg)
    }
}