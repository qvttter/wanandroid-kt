package com.li.mykotlinapp.view.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseDialog
import com.li.mykotlinapp.base.BaseVMDialog
import com.li.mykotlinapp.databinding.DialogLoginBinding
import com.li.mykotlinapp.databinding.FragmentMyBinding
import com.li.mykotlinapp.util.DensityUtil
import com.li.mykotlinapp.view.vm.MyViewModel

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.dialog
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/9/23
 *@Copyright:(C)2021 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class LoginDialog : BaseVMDialog<MyViewModel, DialogLoginBinding>(R.layout.dialog_login) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogLoginBinding
        get() = DialogLoginBinding::inflate

    override fun initData() {
        binding.ivClose.setOnClickListener { dismiss() }

    }

    override fun onResume() {
        dialog!!.window!!.setLayout(
            DensityUtil.dip2px(activity, 350f),
            DensityUtil.dip2px(activity, 380f)
        )
        dialog!!.setCanceledOnTouchOutside(false)
        super.onResume()
    }

    companion object {
        fun newInstance(): LoginDialog = LoginDialog()
    }
}