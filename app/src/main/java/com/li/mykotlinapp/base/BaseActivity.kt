package com.li.mykotlinapp.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.li.mykotlinapp.R
import com.li.mykotlinapp.util.ToastUtil
import com.li.mykotlinapp.util.slideback.SwipeBackHelper
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE
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
    protected var mProgressDialog: MaterialDialog? = null
    protected var loadingDialog: ZLoadingDialog? = null
    private lateinit var mSwipeBackHelper: SwipeBackHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        mContext = this
        initData()
//        mSwipeBackHelper = SwipeBackHelper(this)

    }

//    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
//        return if (mSwipeBackHelper != null && mSwipeBackHelper.dispatchTouchEvent(event)) {
//            true
//        } else super.dispatchTouchEvent(event)
//    }
//
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (mSwipeBackHelper != null) {
//            mSwipeBackHelper.onTouchEvent(event)
//        }
//        return super.onTouchEvent(event)
//    }


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

    fun showLoading(msg: String) {
        if (msg.isEmpty()) {
            showLoading("加载中")
        } else {
            loadingDialog = ZLoadingDialog(mContext)
            loadingDialog!!.setLoadingBuilder(Z_TYPE.CIRCLE)
                .setLoadingColor(resources.getColor(R.color.colorPrimary))//颜色
                .setHintText("加载中")
                .setHintTextSize(16f) // 设置字体大小 dp
                .setHintTextColor(Color.GRAY)  // 设置字体颜色
                .setDurationTime(0.5) // 设置动画时间百分比 - 0.5倍
                .setCanceledOnTouchOutside(false)
                .setDialogBackgroundColor(Color.parseColor("#CC111111")) // 设置背景色，默认白色
                .show()
        }
    }

    fun hideLoading() {
        if (mProgressDialog != null) {
            if (mProgressDialog!!.isShowing) {
                mProgressDialog!!.dismiss()
            }
        }

        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
        }
    }
}