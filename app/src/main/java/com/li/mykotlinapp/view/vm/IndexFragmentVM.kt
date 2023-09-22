package com.li.mykotlinapp.view.vm

import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.base.BaseViewModel
import com.li.mykotlinapp.base.PageDataBean
import com.li.mykotlinapp.base.doFailure
import com.li.mykotlinapp.base.doSuccess
import com.li.mykotlinapp.bean.ArticleBean
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
    val articleListValue =
        MutableStateFlow<PageDataBean<ArticleBean>>(PageDataBean(1, listOf(), 0, false, 0, 0, 0))
    val articleListErrorValue = MutableStateFlow<Boolean>(false)


    /**
     * 首页banner
     */
    fun getMainBanner() {
        launch {
            runIO {
                HttpBiz.getInstance().getMainBanner()
                    .onStart {
                        LogUtils.e("getMainBanner onStart thread " + Thread.currentThread())
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
                            LogUtils.e("getMainBanner doSuccess " + Thread.currentThread())
                            bannerValue.value = value
                        }
                    }
            }
        }
    }

    /**
     *  获取文章
     */
    fun getMainArticleList(page: Int) {
        launchIO {
            HttpBiz.getInstance().getMainArticleList(page)
                .onStart {
                    LogUtils.e("getMainArticleList onStart " + Thread.currentThread())
                }
                .catch {
                    LogUtils.e(it.message)
                    articleListErrorValue.value = true
                }
                .onCompletion {
                }
                .collect { result ->
                    result.doFailure { throwable ->
                        message.value = "获取文章出错"
                    }
                    result.doSuccess { value ->
                        LogUtils.e("获取MainArticleList成功:" + value.datas.size)
                        LogUtils.e("getMainArticleList doSuccess " + Thread.currentThread())
                        articleListValue.value = value

                    }
                }
        }

    }

}