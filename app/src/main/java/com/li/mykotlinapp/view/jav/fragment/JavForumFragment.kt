package com.li.mykotlinapp.view.jav.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentJavCategoryBinding
import com.li.mykotlinapp.databinding.FragmentJavForumBinding

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/25
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
class JavForumFragment : BaseFragment<FragmentJavForumBinding>(R.layout.fragment_jav_forum){

    companion object {
        fun newInstance(): JavMovieFragment = JavMovieFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentJavForumBinding
        get() = FragmentJavForumBinding::inflate

    override fun initData() {

    }
}