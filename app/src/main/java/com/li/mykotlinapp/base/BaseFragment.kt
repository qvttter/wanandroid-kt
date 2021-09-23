package com.li.mykotlinapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.li.mykotlinapp.util.ToastUtil

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/18
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
abstract class BaseFragment<T : ViewBinding>(@LayoutRes val layoutId: Int) : Fragment(layoutId) {
    protected lateinit var mContext: Context
//    private  var _binding: T?=null
//    protected val binding get() = _binding
    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    @Suppress("UNCHECKED_CAST")
    protected val binding: T
        get() = _binding as T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = requireActivity()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

//    protected abstract fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): T

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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