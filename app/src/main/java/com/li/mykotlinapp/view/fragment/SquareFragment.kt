package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentSquareBinding

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/9/24
 *@Copyright:(C)2021 . All rights reserved.
 *************************************************************************/
class SquareFragment:BaseFragment<FragmentSquareBinding>(R.layout.fragment_square) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSquareBinding
        get() = FragmentSquareBinding::inflate

    override fun initData() {
    }
}