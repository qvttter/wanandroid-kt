package com.easyway.ipu.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.li.mykotlinapp.R
import com.li.mykotlinapp.util.CommonUtils
import com.li.mykotlinapp.util.ToastUtil
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE


/************************************************************************
 *@Project: ipu_android
 *@Package_Name: com.easyway.ipu.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020-03-12
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
abstract class BaseVMFragment<VM : BaseViewModel> : Fragment() {
    protected lateinit var mContext: Context
    protected lateinit var mViewModel: VM
    protected var mProgressDialog: MaterialDialog? = null
    protected var loadingDialog: ZLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(getLayout(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initVM()
        initData()
    }

    abstract fun getLayout(): Int
    abstract fun initData()

    private fun initVM() {
        mViewModel = ViewModelProviders.of(this).get(CommonUtils.getClass(this))
    }

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }

    fun shortToast(msg: String) {
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