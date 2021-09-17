package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentSearchBinding

class SearchFragment : BaseFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    override fun getLayout(): Int {
        return R.layout.fragment_search
    }

    override fun initData() {
    }

    companion object {
        fun newInstance(): SearchFragment = SearchFragment()
    }

    override fun getBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?
    ): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater,viewGroup,false)
    }

}