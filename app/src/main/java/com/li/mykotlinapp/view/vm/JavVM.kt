package com.li.mykotlinapp.view.vm

import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.base.BaseViewModel
import com.li.mykotlinapp.base.doFailure
import com.li.mykotlinapp.base.doSuccess
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.bean.jav.JavItemBean
import com.li.mykotlinapp.bean.jav.JavMovieDetailBean
import com.li.mykotlinapp.biz.HttpBiz
import com.li.mykotlinapp.biz.JHttpBiz
import kotlinx.coroutines.flow.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.vm
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/26
 *@Copyright:(C)2022 . All rights reserved.
 *************************************************************************/
class JavVM : BaseViewModel() {
    val mosaicListValue = MutableStateFlow<List<JavItemBean>>(listOf())
    val movieDetailValue = MutableStateFlow<JavMovieDetailBean?>(null)

    //有码列表
    fun getMosaicList(page: Int) {
        launch {
            JHttpBiz.getInstance().getMosaicList(page)
                .onStart {
                    isLoading.value = true
                }
                .catch {
                    LogUtils.e(it.message)
                    isLoading.value = false
                }
                .onCompletion {
                    isLoading.value = false
                }
                .collect { result ->
                    isLoading.value = false

                    result.doFailure { throwable ->
                        message.value = "获取内容出错"
                    }
                    result.doSuccess { value ->
                        mosaicListValue.value = value
                    }
                }
        }
    }

    //电影详情
    fun getMovieDetail(movieCode: String) {
        launch {
            JHttpBiz.getInstance().getMovieDetail(movieCode)
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
                        message.value = "获取内容出错"
                    }
                    result.doSuccess { value ->
                        movieDetailValue.value = value
                    }
                }
        }
    }
    //电影详情
    fun getMyMosaicMovie(page: Int) {
        launch {
            JHttpBiz.getInstance().getMyMosaicMovie(page)
                .onStart {
                    isLoading.value = true
                }
                .catch {
                    LogUtils.e(it.message)
                    isLoading.value = false
                }
                .onCompletion {
                    isLoading.value = false
                }
                .collect { result ->
                    result.doFailure { throwable ->
                        message.value = "获取内容出错"
                    }
                    result.doSuccess { value ->
                        mosaicListValue.value = value
                    }
                }
        }
    }
}