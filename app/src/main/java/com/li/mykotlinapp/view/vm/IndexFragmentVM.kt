package com.li.mykotlinapp.view.vm

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.easyway.ipu.base.BaseViewModel
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.biz.CommonBiz

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.vm
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/9/1
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class IndexFragmentVM : BaseViewModel() {
    val bannerList = MutableLiveData<MutableList<BannerBean>>()

    //    //获取banner
//        fun getMainBanner(){
//        CommonBiz.getInstance().getMainBanner()
//                .subscribe({ t: List<BannerBean> ->
//                    bannerList.clear()
//                    bannerImgList.clear()
//                    for (bannerBean in t) {
//                        bannerImgList.add(bannerBean.imagePath)
//                    }
//                    bannerList.addAll(t)
//                    banner.setImages(bannerImgList)
//                    banner.start()
//                }, { t: Throwable? ->
//                    shortToast(getString(R.string.str_common_error) + t!!.message)
//                })

//        //获取文章
//        CommonBiz.getInstance().getMainArticleList(page)
//                .subscribe({ t: PageDataBean<ArticleBean>? ->
//                    articleList.clear()
//                    articleList.addAll(t!!.datas)
//                    adapter.notifyDataSetChanged()
//                    index_swipe_fresh.isRefreshing = false
//                }, { t: Throwable? ->
//                    shortToast(getString(R.string.str_common_error)+ t!!.message)
//                    index_swipe_fresh.isRefreshing = false
//                })

    fun getMainBanner() {
//        launch {
//            val banners = runIO {
//                CommonBiz.getInstance().getMainBanner()
//            }
//            bannerList.value = banners
//        }
    }

}