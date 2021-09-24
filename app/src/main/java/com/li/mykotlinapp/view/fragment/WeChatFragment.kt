package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentWeChatBinding

class WeChatFragment : BaseFragment<FragmentWeChatBinding>(R.layout.fragment_we_chat) {
    override fun initData() {
    }

    companion object {
        fun newInstance(): WeChatFragment = WeChatFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentWeChatBinding
        get() = FragmentWeChatBinding::inflate

}