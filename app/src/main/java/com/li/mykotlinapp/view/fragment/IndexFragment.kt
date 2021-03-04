package com.li.mykotlinapp.view.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.easyway.ipu.base.BaseVMFragment
import com.li.mykotlinapp.R
import com.li.mykotlinapp.adapter.IndexArticleAdapter
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.common.GlideImageLoader
import com.li.mykotlinapp.view.activity.CommonWebViewActivity
import com.li.mykotlinapp.view.vm.IndexFragmentVM
import com.youth.banner.Banner
import kotlinx.android.synthetic.main.fragment_index.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.fragment
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/11/5
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class IndexFragment : BaseVMFragment<IndexFragmentVM>() {
    var bannerList: MutableList<BannerBean> = ArrayList()
    var bannerImgList: MutableList<String> = ArrayList()

    var articleList: MutableList<ArticleBean> = ArrayList()
    lateinit var adapter: IndexArticleAdapter
    lateinit var bannerView: View
    lateinit var banner: Banner
    var page = 0

    companion object {
        fun newInstance(): IndexFragment = IndexFragment()
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
        banner = bannerView.findViewById(R.id.banner)
        banner.setImageLoader(GlideImageLoader())
        banner.setDelayTime(4000)
        adapter.addHeaderView(bannerView)

        //swipe颜色
        index_swipe_fresh.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        mViewModel.apply {
            isLoading.observe(this@IndexFragment, Observer {
            })

            message.observe(this@IndexFragment, Observer {
            })
        }
        getData()
        initEvent()

    }

    private fun getData() {
        mViewModel.getMainBanner()
    }

    private fun initEvent() {
        banner.setOnBannerListener { position: Int ->
            val bean = bannerList[position]
            CommonWebViewActivity.start(mContext,bean.url ,bean.title)
        }

        index_swipe_fresh.setOnRefreshListener {
            getData()
        }
    }
}