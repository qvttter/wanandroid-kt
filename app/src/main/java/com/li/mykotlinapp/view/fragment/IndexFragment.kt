package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.li.mykotlinapp.adapter.IndexArticleAdapter
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.view.vm.IndexFragmentVM
import com.stx.xhb.androidx.XBanner
import kotlinx.android.synthetic.main.fragment_index.*
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.apkfuns.logutils.LogUtils


import com.bumptech.glide.Glide
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseVMFragment
import com.li.mykotlinapp.base.doFailure
import com.li.mykotlinapp.base.doSuccess
import com.li.mykotlinapp.biz.HttpBiz
import com.li.mykotlinapp.databinding.FragmentIndexBinding
import com.li.mykotlinapp.view.activity.CommonWebViewActivity
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/11/5
 
 *************************************************************************/
class IndexFragment :
    BaseVMFragment<IndexFragmentVM, FragmentIndexBinding>(R.layout.fragment_index) {
    private var articleList: MutableList<ArticleBean> = ArrayList()
    lateinit var adapter: IndexArticleAdapter
    private lateinit var bannerView: View
    private lateinit var banner: XBanner

    private var page = 0

    companion object {
        fun newInstance(): IndexFragment = IndexFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIndexBinding
        get() = FragmentIndexBinding::inflate


    override fun initData() {
        //文章
        adapter = IndexArticleAdapter(articleList)
        binding.rcvMain.adapter = adapter
        binding.rcvMain.layoutManager = LinearLayoutManager(mContext)
        binding.rcvMain.isNestedScrollingEnabled = false
        binding.rcvMain.addItemDecoration(DividerItemDecoration(mContext,LinearLayoutManager.VERTICAL))

        adapter.loadMoreModule.isAutoLoadMore = true
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false

        //banner
        bannerView = layoutInflater.inflate(R.layout.include_banner, null)
        banner = bannerView.findViewById(R.id.xbanner)
        banner.loadImage { banner, model, view, position ->
            Glide.with(mContext).load((model as BannerBean).imagePath)
                .into(view as ImageView)
        }
        adapter.addHeaderView(bannerView)

        //swipe颜色
        index_swipe_fresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))

        lifecycleScope.launch {
            mViewModel.bannerValue.collect {
                LogUtils.e("收到banner" + it.size)
                if (it.isNotEmpty()) {
                    banner.setBannerData(it)
                }
            }
        }
        lifecycleScope.launch {
            mViewModel.isLoading.collect {
                index_swipe_fresh.isRefreshing = it
            }
        }

        lifecycleScope.launch {
            mViewModel.message.collect {
                shortToast(it)
            }
        }
        initEvent()

        getData(false)
    }

    private fun getData(isLoadMore: Boolean) {
        mViewModel.getMainBanner()
        getMainArticleList(isLoadMore)
    }

    private fun getMainArticleList(isLoadMore: Boolean) {
        lifecycleScope.launch {
            HttpBiz.getInstance().getMainArticleList(page)
                .onStart {
                }
                .catch {
                    LogUtils.e(it.message)
                    if (isLoadMore) {
                        page--
                        adapter.loadMoreModule.loadMoreFail()
                    } else {
                        binding.indexSwipeFresh.isRefreshing = false
                    }
                }
                .onCompletion {
                }
                .collect { result ->
                    result.doFailure { throwable ->
                        shortToast("获取MainArticleList出错")
                    }
                    result.doSuccess { value ->
                        LogUtils.e("获取MainArticleList成功:"+value.datas.size)
                        if (isLoadMore) {
                            if (adapter.itemCount >= value.total) {
                                //数据全部加载完毕.false:显示“没有更多了”
                                adapter.loadMoreModule.loadMoreEnd()
                            } else {
                                //成功获取更多数据
                                articleList.addAll(value.datas)
                                adapter.addData(value.datas)
                                adapter.loadMoreModule.loadMoreComplete()
                            }
                        } else {
                            articleList.clear()
                            articleList.addAll(value.datas)
                            adapter.setList(articleList)
                            binding.indexSwipeFresh.isRefreshing = false
                        }
                    }
                }
        }
    }

    private fun initEvent() {
        banner.setOnItemClickListener { banner, model, view, position ->
            val bean = model as BannerBean
            CommonWebViewActivity.start(mContext, bean.url, bean.title)
        }

        binding.indexSwipeFresh.setOnRefreshListener {
            page = 0
            adapter.loadMoreModule.isEnableLoadMore = false
            getData(false)
        }

        adapter.loadMoreModule.setOnLoadMoreListener {
            page++
            getMainArticleList(true)
        }

        adapter.setOnItemClickListener { adapter, view, position ->
            val bean = adapter.getItem(position) as ArticleBean
            CommonWebViewActivity.start(mContext, bean.link, bean.title)
        }
    }
}