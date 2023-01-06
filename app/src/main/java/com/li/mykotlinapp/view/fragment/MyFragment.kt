package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.afollestad.materialdialogs.MaterialDialog
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseVMFragment
import com.li.mykotlinapp.databinding.FragmentMyBinding
import com.li.mykotlinapp.util.PrefUtil
import com.li.mykotlinapp.view.activity.TestDoubleScreenActivity
import com.li.mykotlinapp.view.activity.TestKtActivity
import com.li.mykotlinapp.view.dialog.LoginDialog
import com.li.mykotlinapp.view.jav.activity.JavMainActivity
import com.li.mykotlinapp.view.vm.MyViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyFragment : BaseVMFragment<MyViewModel, FragmentMyBinding>(R.layout.fragment_my) {
    override fun initData() {
        lifecycleScope.launch {
            mViewModel.logoutValue.collect {
                if (it) isLogin()
            }
        }

        lifecycleScope.launch {
            mViewModel.isLoading.collect {
                if (it) {
                    showLoading("")
                } else {
                    hideLoading()
                }
            }
        }

        isLogin()

        binding.tvMyCollectArticle.setOnLongClickListener {
            JavMainActivity.start(mContext)
            true
        }

        binding.tvMyShareProject.setOnLongClickListener {
            TestDoubleScreenActivity.start(mContext)
            true
        }

        binding.tvName.setOnClickListener {
            val loginDialog = LoginDialog.newInstance()
            loginDialog.setListener(object : LoginDialog.OnLoginListener {
                override fun onLogin() {
                    binding.tvName.text = PrefUtil.userName
                    binding.tvName.isClickable = false
                }
            })
            loginDialog.show(parentFragmentManager, "LoginDialog")
        }

        binding.btnLogout.setOnClickListener {
            if(PrefUtil.flagLogin){
                MaterialDialog(mContext).show {
                    message (text= "确定退出登录？")
                    positiveButton(R.string.str_confirm){
                        mViewModel.logout()
                    }
                    negativeButton(text = "取消")
                }
            }else{
                MaterialDialog(mContext).show {
                    message (text= "请先登录")
                    positiveButton(R.string.str_confirm)
                }
            }
        }

        binding.tvTest.setOnClickListener {
            TestKtActivity.start(mContext)
        }

    }

    private fun logout(){

    }

    private fun isLogin() {
        if (PrefUtil.flagLogin) {
            binding.tvName.text = PrefUtil.userName
            binding.tvName.isClickable = false
        } else {
            binding.tvName.text = "登录"
            binding.tvName.isClickable = true
        }
    }

    companion object {
        fun newInstance(): MyFragment = MyFragment()
    }

//    override fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): FragmentMyBinding {
//        return FragmentMyBinding.inflate(inflater,viewGroup,false)
//    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMyBinding
        get() = FragmentMyBinding::inflate
}