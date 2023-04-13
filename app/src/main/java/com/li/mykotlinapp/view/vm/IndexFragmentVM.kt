package com.li.mykotlinapp.view.vm

import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.base.BaseViewModel
import com.li.mykotlinapp.base.doFailure
import com.li.mykotlinapp.base.doSuccess
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.biz.HttpBiz
import kotlinx.coroutines.flow.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.vm
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/9/1
 *@Copyright:(C)2020 . All rights reserved.
 *************************************************************************/
class IndexFragmentVM : BaseViewModel() {
    val bannerValue = MutableStateFlow<List<BannerBean>>(listOf())
//    val articleListValue = MutableStateFlow<PageDataBean<ArticleBean>>()

    //获取文章
    fun getMainArticleList(page: Int) {
//        launch {
//            HttpBiz.getInstance().getMainArticleList(page)
//                .onStart {
//                }
//                .catch {
//                    LogUtils.e(it.message)
//                    if (isLoadMore) {
//                        page--
//                        adapter.loadMoreModule.loadMoreFail()
//                    } else {
//                        binding.indexSwipeFresh.isRefreshing = false
//                    }
//                }
//                .onCompletion {
//                }
//                .collect { result ->
//                    result.doFailure { throwable ->
//                        shortToast("获取MainArticleList出错")
//                    }
//                    result.doSuccess { value ->
//                        LogUtils.e("获取MainArticleList成功:"+value.datas.size)
//                        if (isLoadMore) {
//                            if (adapter.itemCount >= value.total) {
//                                //数据全部加载完毕.false:显示“没有更多了”
//                                adapter.loadMoreModule.loadMoreEnd()
//                            } else {
//                                //成功获取更多数据
//                                articleList.addAll(value.datas)
//                                adapter.addData(value.datas)
//                                adapter.loadMoreModule.loadMoreComplete()
//                            }
//                        } else {
//                            articleList.clear()
//                            articleList.addAll(value.datas)
//                            adapter.setList(articleList)
//                            binding.indexSwipeFresh.isRefreshing = false
//                        }
//                    }
//                }
//        }
    }

    fun getMainBanner() {
        launch {
            HttpBiz.getInstance().getMainBanner()
                .onStart {
                    isLoading.value = true
                }
                .catch {
                    LogUtils.e(it.message)
                }
                .onCompletion {
                    isLoading.value = false
                }
                .collect { result ->
                    result.doFailure { throwable ->
                        message.value = "获取banner出错"
                    }
                    result.doSuccess { value ->
                        bannerValue.value = value
                    }
                }
        }
    }
}