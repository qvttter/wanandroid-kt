package com.li.mykotlinapp.view.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseDialog
import com.li.mykotlinapp.base.BaseVMDialog
import com.li.mykotlinapp.databinding.DialogLoginBinding
import com.li.mykotlinapp.databinding.FragmentMyBinding
import com.li.mykotlinapp.util.DensityUtil
import com.li.mykotlinapp.util.PrefUtil
import com.li.mykotlinapp.view.vm.MyViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.dialog
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/9/23
 *@Copyright:(C)2021 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class LoginDialog : BaseVMDialog<MyViewModel, DialogLoginBinding>(R.layout.dialog_login) {
    private var listener:OnLoginListener?=null


    interface OnLoginListener{
        fun onLogin()
    }

    fun setListener(listener:OnLoginListener){
        this.listener = listener
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> DialogLoginBinding
        get() = DialogLoginBinding::inflate

    override fun initData() {
        binding.ivClose.setOnClickListener { dismiss() }

        binding.tvForgetPsw.setOnClickListener {

        }

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val psw = binding.etPsw.text.toString()
            if (username.isEmpty() || psw.isEmpty()){
                shortToast("请输入用户名密码")
                return@setOnClickListener
            }
            mViewModel.login(username,psw)
        }

        lifecycleScope.launch {
            mViewModel.loginValue.collect {
                if (it!=null) {
                    if (it){
                        shortToast("登录成功")
                        if (listener!=null){
                            listener!!.onLogin()
                            dismiss()
                        }
                    }else{
                        shortToast("登录失败")
                    }
                }
            }
        }

        lifecycleScope.launch {
            mViewModel.isLoading.collect {
                if (it){
                    showLoading("登录中，请稍后")
                }else{
                    hideLoading()
                }
            }
        }

    }

    override fun onResume() {
        dialog!!.window!!.setLayout(
            DensityUtil.dip2px(activity, 350f),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog!!.setCanceledOnTouchOutside(false)
        super.onResume()
    }

    companion object {
        fun newInstance(): LoginDialog = LoginDialog()
    }
}