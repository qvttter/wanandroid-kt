package com.li.mykotlinapp.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyway.ipu.base.BaseVMFragment
import com.li.mykotlinapp.adapter.IndexArticleAdapter
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.view.vm.IndexFragmentVM
import com.stx.xhb.androidx.XBanner
import kotlinx.android.synthetic.main.fragment_index.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.widget.ImageView
import com.apkfuns.logutils.LogUtils


import com.bumptech.glide.Glide
import com.li.mykotlinapp.R
import com.li.mykotlinapp.base.BaseFragment
import com.li.mykotlinapp.databinding.FragmentIndexBinding
import com.stx.xhb.androidx.XBanner.XBannerAdapter
import com.li.mykotlinapp.view.activity.CommonWebViewActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/11/5
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class IndexFragment : BaseVMFragment<IndexFragmentVM,FragmentIndexBinding>(R.layout.fragment_index) {
    private var bannerList: MutableList<BannerBean> = ArrayList()
    private var bannerImgList: MutableList<String> = ArrayList()

    private var articleList: MutableList<ArticleBean> = ArrayList()
    lateinit var adapter: IndexArticleAdapter
    private lateinit var bannerView: View
    private lateinit var banner: XBanner

    private var page = 0

    companion object {
        fun newInstance(): IndexFragment = IndexFragment()
    }

    override fun initBinding(view: View): FragmentIndexBinding {
        return FragmentIndexBinding.bind(view)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_index
    }

    override fun initData() {
        //文章
        adapter = IndexArticleAdapter(articleList)
        rcv_main.adapter = adapter
        rcv_main.layoutManager = LinearLayoutManager(mContext)
        rcv_main.isNestedScrollingEnabled = false

        //banner
        bannerView = layoutInflater.inflate(R.layout.include_banner, null)
        banner = bannerView.findViewById(R.id.xbanner)
        banner.loadImage(XBannerAdapter { banner, model, view, position -> //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
            Glide.with(mContext).load((model as BannerBean).imagePath)
                .into(view as ImageView)
        })
        adapter.addHeaderView(bannerView)

        //swipe颜色
        index_swipe_fresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))

        lifecycleScope.launch {
            mViewModel.bannerValue.collect {
                LogUtils.e("收到banner" + it.size)
                banner.setBannerData(it)
            }
        }
        lifecycleScope.launch {
            mViewModel.isLoading.collect {
                LogUtils.e("mViewModel.isLoading:"+it)
                index_swipe_fresh.isRefreshing = it
            }
        }

        lifecycleScope.launch {
            mViewModel.message.collect {
                shortToast(it)
            }
        }
        getData()
        initEvent()
    }

    private fun getData() {
        mViewModel.getMainBanner()
    }

    private fun initEvent() {
        banner.setOnItemClickListener { banner, model, view, position ->
            var bean = model as BannerBean
            CommonWebViewActivity.start(mContext, bean.url, bean.title)
        }

        index_swipe_fresh.setOnRefreshListener {
            getData()
        }
    }


}