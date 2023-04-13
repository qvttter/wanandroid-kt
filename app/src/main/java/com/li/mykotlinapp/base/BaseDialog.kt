package com.li.mykotlinapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.li.mykotlinapp.util.ToastUtil

/************************************************************************
 *@Project: ipu_android
 *@Package_Name: com.easyway.ipu.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/6/23
 *@Copyright:(C)2020 . All rights reserved.
 *************************************************************************/
abstract class BaseDialog : DialogFragment() {
    protected lateinit var mContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireActivity()
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    abstract fun getLayout(): Int
    abstract fun initData()

    fun shortToast(msg: String) {
        ToastUtil.shortToast(mContext,msg)
    }

    fun longToast(msg: String) {
        ToastUtil.longToast(mContext,msg)
    }

}