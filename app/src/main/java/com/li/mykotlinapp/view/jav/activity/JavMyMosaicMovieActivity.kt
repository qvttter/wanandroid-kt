package com.li.mykotlinapp.view.jav.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.jav.CommonMovieAdapter
import com.li.mykotlinapp.base.BaseVMActivity
import com.li.mykotlinapp.bean.jav.JavItemBean
import com.li.mykotlinapp.databinding.ActivityJavMyMosaicMovieBinding
import com.li.mykotlinapp.view.vm.JavVM
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.jav.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/28
 *************************************************************************/
class JavMyMosaicMovieActivity : BaseVMActivity<JavVM, ActivityJavMyMosaicMovieBinding>() {
    private lateinit var adapter: CommonMovieAdapter
    private lateinit var list:MutableList<JavItemBean>

    override val bindingInflater: (LayoutInflater) -> ActivityJavMyMosaicMovieBinding
        get() = ActivityJavMyMosaicMovieBinding::inflate

    override fun initData() {
        lifecycleScope.launch {
            mViewModel.mosaicListValue.collect {
                if (it.isNotEmpty()) {
                    LogUtils.e("JavMyMosaicMovieActivity 收到mosaicListValue" + it.size)
                    list.clear()
                    list.addAll(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        lifecycleScope.launch {
            mViewModel.isLoading.collect {
                LogUtils.e("isloading"+it)
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
        binding.rcvMyMosaicMovie.adapter = adapter
        binding.rcvMyMosaicMovie.layoutManager =
            (StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL))

        adapter.setOnItemClickListener { adapter, view, position ->
            var bean = adapter.getItem(position) as JavItemBean
            JavMovieDetailActivity.start(mContext,bean.code)

        }

        getData(1)
    }

    private fun getData(page:Int){
        mViewModel.getMyMosaicMovie(page)

    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, JavMyMosaicMovieActivity().javaClass)
            content.startActivity(intent)
        }
    }
}