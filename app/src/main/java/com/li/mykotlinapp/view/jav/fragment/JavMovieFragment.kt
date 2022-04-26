package com.li.mykotlinapp.view.jav.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentJavMovieBinding
import com.li.mykotlinapp.view.fragment.IndexFragment
import com.li.mykotlinapp.view.fragment.SquareFragment

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/25
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class JavMovieFragment : BaseFragment<FragmentJavMovieBinding>(R.layout.fragment_jav_movie) {

    companion object {
        fun newInstance(): JavMovieFragment = JavMovieFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentJavMovieBinding
        get() = FragmentJavMovieBinding::inflate

    override fun initData() {
        val titleList =   arrayOf(mContext.getString(R.string.mosaic), mContext.getString(R.string.noMosaic))
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = when (position) {
                0 -> MosaicMovieFragment()
                1 -> NoMosaicMovieFragment()
                else -> MosaicMovieFragment()
            }
            override fun getItemCount() = titleList.size
        }

        binding.ivSearch.setOnClickListener {

        }


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

}