package com.li.mykotlinapp.view.jav.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.jav.CommonMovieAdapter
import com.li.mykotlinapp.base.BaseVMFragment
import com.li.mykotlinapp.bean.jav.JavItemBean
import com.li.mykotlinapp.databinding.FragmentMosaicBinding
import com.li.mykotlinapp.view.jav.activity.JavMovieDetailActivity
import com.li.mykotlinapp.view.vm.JavVM
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/26
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
class MosaicMovieFragment :BaseVMFragment<JavVM, FragmentMosaicBinding>(R.layout.fragment_mosaic) {

    private lateinit var adapter: CommonMovieAdapter
    private lateinit var list:MutableList<JavItemBean>
    companion object {
        fun newInstance(): MosaicMovieFragment = MosaicMovieFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMosaicBinding
        get() = FragmentMosaicBinding::inflate

    override fun initData() {
        lifecycleScope.launch {
            mViewModel.mosaicListValue.collect {
                if (it.isNotEmpty()) {
                    LogUtils.e("收到mosaicListValue" + it.size)
                    list.clear()
                    list.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }

            mViewModel.isLoading.collect {
                binding.swipeFresh.isRefreshing = it
            }
        }

        binding.swipeFresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        binding.swipeFresh.setOnRefreshListener {
            getData(1)
        }

        list = ArrayList()
        adapter = CommonMovieAdapter(list)
        adapter.data = list
        binding.rcvMosaic.adapter = adapter
        binding.rcvMosaic.layoutManager =
            (StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))

        adapter.setOnItemClickListener { adapter, view, position ->
            var bean = adapter.getItem(position) as JavItemBean
            JavMovieDetailActivity.start(mContext,bean.code)

        }

        getData(1)
    }

    private fun getData(page:Int){
        mViewModel.getMosaicList(page)

    }


}