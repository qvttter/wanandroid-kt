package com.li.mykotlinapp.view.jav.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentJavTabBinding
import kotlinx.android.synthetic.main.fragment_home_tab.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/25
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
class JavHomeTabFragment : BaseFragment<FragmentJavTabBinding>(R.layout.fragment_jav_tab) {

    override fun initData() {
        initViewPager()
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelected)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentJavTabBinding
        get() = FragmentJavTabBinding::inflate

    private val onNavigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.action_movie -> {
                switchFragment(0)
            }
            R.id.action_category -> {
                switchFragment(1)
            }
            R.id.action_forum -> {
                switchFragment(2)
            }

            R.id.action_my -> {
                switchFragment(3)
            }
        }
        true
    }

    private fun switchFragment(position: Int): Boolean {
        mainViewpager.setCurrentItem(position, false)
        return true
    }

    private fun initViewPager() {
        mainViewpager.isUserInputEnabled = false
        mainViewpager.offscreenPageLimit = 2
        mainViewpager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment = when (position) {
                0 -> JavMovieFragment()
                1 -> JavCategoryFragment()
                2 -> JavForumFragment()
                3 -> JavMyFragment()
                else -> JavMovieFragment()
            }
            override fun getItemCount() = 4
        }
    }
}