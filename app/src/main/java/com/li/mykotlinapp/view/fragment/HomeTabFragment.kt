package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentHomeTabBinding
import kotlinx.android.synthetic.main.fragment_home_tab.*

/**
 * 这是首页 Tab
 */
class HomeTabFragment : BaseFragment<FragmentHomeTabBinding>(R.layout.fragment_home_tab) {

    override fun initData() {
        initViewPager()
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelected)
    }

//    override fun getBinding(
//        inflater: LayoutInflater,
//        viewGroup: ViewGroup?
//    ): FragmentHomeTabBinding {
//        return FragmentHomeTabBinding.inflate(inflater, viewGroup, false)
//    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeTabBinding
        get() = FragmentHomeTabBinding::inflate

    private val onNavigationItemSelected = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.action_home -> {
                switchFragment(0)
            }
            R.id.action_wechat -> {
                switchFragment(1)
            }
            R.id.action_search -> {
                switchFragment(2)
            }

            R.id.action_project -> {
                switchFragment(3)
            }
            R.id.profile -> {
                switchFragment(4)
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
                0 -> IndexFragment()
                1 -> WeChatFragment()
                2 -> SearchFragment()
                3 -> ProjectFragment()
                4 -> MyFragment()
                else -> IndexFragment()
            }
            override fun getItemCount() = 5
        }
    }


}
