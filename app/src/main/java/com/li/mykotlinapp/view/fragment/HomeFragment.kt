package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentHomeBinding

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/9/24
 *@Copyright:(C)2021 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    //    private val titleList = arrayOf("首页", "广场", "最新项目", "体系", "导航")
    private val titleList = arrayOf("首页", "广场")
    private var mOnPageChangeCallback: ViewPager2.OnPageChangeCallback? = null


    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    override fun initData() {
        binding.viewPager.offscreenPageLimit = 1
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int) = when (position) {
                0 -> IndexFragment()
                1 -> SquareFragment()
//                2 -> ProjectTypeFragment.newInstance(0, true)
//                3 -> SystemFragment()
//                4 -> NavigationFragment()
                else -> IndexFragment()
            }

            override fun getItemCount() = titleList.size
        }


        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        if (mOnPageChangeCallback == null) mOnPageChangeCallback =
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    if (position == 1) binding.addFab.show() else binding.addFab.hide()
                }
            }
        mOnPageChangeCallback?.let { binding.viewPager.registerOnPageChangeCallback(it) }
    }

    override fun onPause() {
        super.onPause()
        mOnPageChangeCallback?.let { binding.viewPager.unregisterOnPageChangeCallback(it) }
    }
}