package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentProjectBinding

class ProjectFragment : BaseFragment<FragmentProjectBinding>(R.layout.fragment_project) {
    override fun getLayout(): Int {
        return R.layout.fragment_project
    }

    override fun initData() {
    }

    companion object {
        fun newInstance(): ProjectFragment = ProjectFragment()
    }

    override fun getBinding(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?
    ): FragmentProjectBinding {
        return FragmentProjectBinding.inflate(inflater,viewGroup,false)
    }
}