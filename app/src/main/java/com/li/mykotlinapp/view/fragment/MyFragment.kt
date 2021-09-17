package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentMyBinding

class MyFragment : BaseFragment<FragmentMyBinding>(R.layout.fragment_my) {
    override fun getLayout(): Int {
        return R.layout.fragment_my
    }

    override fun initData() {
    }

    companion object {
        fun newInstance(): MyFragment = MyFragment()
    }

    override fun getBinding(inflater: LayoutInflater, viewGroup: ViewGroup?): FragmentMyBinding {
        return FragmentMyBinding.inflate(inflater,viewGroup,false)
    }
}