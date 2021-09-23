package com.li.mykotlinapp.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.li.mykotlinapp.R
import com.li.mykotlinapp.util.CommonUtils
import com.li.mykotlinapp.util.ToastUtil
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE
import kotlinx.android.synthetic.main.include_toolbar.*

/************************************************************************
 *@Project: ipu_android
 *@Package_Name: com.easyway.ipu.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020-01-06
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
abstract class BaseVMActivity<VM : BaseViewModel,T : ViewBinding> : AppCompatActivity() {
    protected lateinit var mContext: Context
    protected lateinit var mViewModel: VM
    protected var mProgressDialog: MaterialDialog? = null
    protected var loadingDialog: ZLoadingDialog? = null

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> T

    @Suppress("UNCHECKED_CAST")
    protected val binding: T
        get() = _binding as T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        initVM()
        initData()
    }

//    protected abstract fun getLayout(): Int
    protected abstract fun initData()

    override fun onDestroy() {
        mViewModel.let {
            lifecycle.removeObserver(it)
        }
        super.onDestroy()
        _binding = null
    }

    private fun initVM() {
        mViewModel = ViewModelProvider(this)[CommonUtils.getClass(this)]

//        providerVMClass()?.let {
//            mViewModel = ViewModelProviders.of(this).get(it)
//            mViewModel.let(lifecycle::addObserver)
//        }
    }

    open fun providerVMClass(): Class<VM>? = null

    /**
     * 处理通用toolbar，需要在布局里<include layout="@layout/common_toolbar" />
     * 暂时只处理带返回按钮的。
     */
    fun initToolBar(title: String) {
        tool_bar.title = title
        tool_bar.setNavigationOnClickListener { finish() }
    }


    fun shortToast(msg: String?) {
        ToastUtil.shortToast(mContext,msg)
    }

    fun longToast(msg: String) {
        ToastUtil.longToast(mContext,msg)
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