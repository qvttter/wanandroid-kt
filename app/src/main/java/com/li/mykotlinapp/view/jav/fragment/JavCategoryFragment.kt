package com.li.mykotlinapp.view.jav.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentJavCategoryBinding
import com.li.mykotlinapp.databinding.FragmentJavMovieBinding

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/25
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class JavCategoryFragment : BaseFragment<FragmentJavCategoryBinding>(R.layout.fragment_jav_category){

    companion object {
        fun newInstance(): JavMovieFragment = JavMovieFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentJavCategoryBinding
        get() = FragmentJavCategoryBinding::inflate

    override fun initData() {

    }
}