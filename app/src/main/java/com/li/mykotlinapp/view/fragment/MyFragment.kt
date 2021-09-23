package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.base.BaseVMFragment
import com.li.mykotlinapp.databinding.FragmentMyBinding
import com.li.mykotlinapp.test.TestComposeActivity
import com.li.mykotlinapp.util.PrefUtil
import com.li.mykotlinapp.view.activity.TestKtActivity
import com.li.mykotlinapp.view.dialog.LoginDialog
import com.li.mykotlinapp.view.vm.MyViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MyFragment : BaseVMFragment<MyViewModel,FragmentMyBinding>(R.layout.fragment_my) {
    override fun getLayout(): Int {
        return R.layout.fragment_my
    }

    override fun initData() {
        lifecycleScope.launch {
            mViewModel.loginValue.collect {
                LogUtils.e("收到login" + it)
            }
        }

        binding.tvName.setOnClickListener {
            var loginDialog = LoginDialog.newInstance()
            loginDialog.show(parentFragmentManager,"LoginDialog" )
        }

        if (PrefUtil.flagLogin){
            binding.tvName.text = PrefUtil.userName
            binding.tvName.isClickable = false
        }else{
            binding.tvName.text = "登录"
            binding.tvName.isClickable = true
        }

        binding.btnLogout.setOnLongClickListener {
            TestKtActivity.start(mContext)
            true
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