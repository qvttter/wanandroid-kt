package com.li.mykotlinapp.view.vm

import androidx.lifecycle.viewModelScope
import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.base.BaseViewModel
import com.li.mykotlinapp.base.PageDataBean
import com.li.mykotlinapp.base.doFailure
import com.li.mykotlinapp.base.doSuccess
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.biz.CommonBiz
import kotlinx.coroutines.flow.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.vm
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2020/9/1
 *@Copyright:(C)2020 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class IndexFragmentVM : BaseViewModel() {
    val bannerValue = MutableStateFlow<List<BannerBean>>(listOf())
//    val articleListValue = MutableStateFlow<PageDataBean<ArticleBean>>()

    //获取文章
    fun getMainArticleList(page: Int) {
        launch {
            CommonBiz.getInstance().getMainArticleList(page)
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
                        message.value = "获取MainArticleList出错"
                    }
                    result.doSuccess { value ->

                    }
                }
        }
    }

    fun getMainBanner() {
        launch {
            CommonBiz.getInstance().getMainBanner()
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