package com.li.mykotlinapp.view.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.CommonStringAdapter
import com.li.mykotlinapp.adapter.IndexArticleAdapter
import com.li.mykotlinapp.base.BaseVMActivity
import com.li.mykotlinapp.base.doFailure
import com.li.mykotlinapp.base.doSuccess
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.db.UserDbBean
import com.li.mykotlinapp.biz.HttpBiz
import com.li.mykotlinapp.databinding.ActivityMyCollectArticleBinding
import com.li.mykotlinapp.databinding.ActivityObjectBoxBinding
import com.li.mykotlinapp.view.vm.MyViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.activity
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/6/13
 *@Copyright:(C)2023 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class MyCollectArticleActivity : BaseVMActivity<MyViewModel, ActivityMyCollectArticleBinding>() {
    private var page = 0
    private var articleList: MutableList<ArticleBean> = ArrayList()
    lateinit var adapter: IndexArticleAdapter

    override val bindingInflater: (LayoutInflater) -> ActivityMyCollectArticleBinding
        get() = ActivityMyCollectArticleBinding::inflate

    override fun initData() {
        binding.srArticle.setColorSchemeColors(resources.getColor(R.color.colorPrimary))

        articleList = ArrayList()
        adapter = IndexArticleAdapter(articleList)
        binding.rcvArticle.adapter = adapter
        binding.rcvArticle.layoutManager = LinearLayoutManager(mContext)
        binding.rcvArticle.isNestedScrollingEnabled = false
        binding.rcvArticle.addItemDecoration(
            DividerItemDecoration(mContext,
                LinearLayoutManager.VERTICAL)
        )

        adapter.loadMoreModule.isAutoLoadMore = true
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        adapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false


        binding.srArticle.setOnRefreshListener {
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

        getData(false)
    }

    private fun getData(isLoadMore: Boolean) {
        mViewModel.getCollectArticleList()
        getMainArticleList(isLoadMore)
    }

    private fun getMainArticleList(isLoadMore: Boolean) {
        lifecycleScope.launch {
            HttpBiz.getInstance().getMyCollectArticleList(page)
                .onStart {
                }
                .catch {
                    LogUtils.e(it.message)
                    if (isLoadMore) {
                        page--
                        adapter.loadMoreModule.loadMoreFail()
                    } else {
                        binding.srArticle.isRefreshing = false
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
                            binding.srArticle.isRefreshing = false
                        }
                    }
                }
        }
    }

    companion object {
        fun start(content: Context) {
            val intent = Intent(content, MyCollectArticleActivity().javaClass)
            content.startActivity(intent)
        }
    }

}