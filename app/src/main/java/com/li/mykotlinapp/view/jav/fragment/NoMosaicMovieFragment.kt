package com.li.mykotlinapp.view.jav.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseVMFragment
import com.li.mykotlinapp.databinding.FragmentNoMosaicBinding
import com.li.mykotlinapp.view.vm.JavVM

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/26
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class NoMosaicMovieFragment : BaseVMFragment<JavVM, FragmentNoMosaicBinding>(R.layout.fragment_no_mosaic) {

    companion object {
        fun newInstance(): NoMosaicMovieFragment = NoMosaicMovieFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentNoMosaicBinding
        get() = FragmentNoMosaicBinding::inflate

    override fun initData() {

    }


}