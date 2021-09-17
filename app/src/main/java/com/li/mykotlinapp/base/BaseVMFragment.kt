package com.easyway.ipu.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.util.CommonUtils
import com.li.mykotlinapp.util.ToastUtil
import com.zyao89.view.zloading.ZLoadingDialog
import com.zyao89.view.zloading.Z_TYPE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


/************************************************************************
 *@Project: ipu_android
 *@Package_Name: com.easyway.ipu.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020-03-12
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
abstract class BaseVMFragment<VM : BaseViewModel, T : ViewBinding>(@LayoutRes val layoutId: Int) :
    Fragment(layoutId) {
    protected lateinit var mContext: Context
    private var mProgressDialog: MaterialDialog? = null
    private var loadingDialog: ZLoadingDialog? = null
    protected lateinit var mViewModel: VM
    private lateinit var _binding: T
    protected val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = initBinding(view)
        initVM()
        initData()
    }

    abstract fun initBinding(view: View): T

    abstract fun getLayout(): Int
    abstract fun initData()

    private fun initVM() {
        mViewModel = ViewModelProvider(this)[CommonUtils.getClass(this)]
    }

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }

    fun shortToast(msg: String) {
        if (msg.isEmpty()) return
        ToastUtil.shortToast(mContext, msg)
    }

    fun longToast(msg: String) {
        if (msg.isEmpty()) return
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