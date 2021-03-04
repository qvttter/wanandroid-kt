package com.li.mykotlinapp.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.li.mykotlinapp.util.ToastUtil

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/18
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
abstract class BaseFragment : Fragment() {
    protected lateinit var mContext: Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(getLayout(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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