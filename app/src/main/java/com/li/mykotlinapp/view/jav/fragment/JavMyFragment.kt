package com.li.mykotlinapp.view.jav.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentJavMyBinding
import com.li.mykotlinapp.util.PrefUtil
import com.li.mykotlinapp.view.jav.activity.JavMyMosaicMovieActivity

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/25
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class JavMyFragment : BaseFragment<FragmentJavMyBinding>(R.layout.fragment_jav_my){

    companion object {
        fun newInstance(): JavMovieFragment = JavMovieFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentJavMyBinding
        get() = FragmentJavMyBinding::inflate

    override fun initData() {
        PrefUtil.jUrl = "https://www.seejav.co"
        binding.tvJavHost.setOnClickListener {
            MaterialDialog(mContext).show {
                input(prefill = PrefUtil.jUrl) { dialog, text ->
                    PrefUtil.jUrl = text.toString()
                }
                positiveButton(R.string.str_confirm)
            }
        }

        binding.tvMyMosaicMovie.setOnClickListener {
            JavMyMosaicMovieActivity.start(mContext)
        }


    }
}