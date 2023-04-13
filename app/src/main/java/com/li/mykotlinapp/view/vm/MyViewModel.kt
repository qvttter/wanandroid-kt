package com.li.mykotlinapp.view.vm

import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.base.BaseViewModel
import com.li.mykotlinapp.base.doFailure
import com.li.mykotlinapp.base.doSuccess
import com.li.mykotlinapp.biz.HttpBiz
import com.li.mykotlinapp.util.PrefUtil
import kotlinx.coroutines.flow.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.view.vm
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2021/9/23
 *@Copyright:(C)2021 . All rights reserved.
 *************************************************************************/
class MyViewModel : BaseViewModel() {
    val loginValue = MutableStateFlow<Boolean?>(null)
    val logoutValue = MutableStateFlow<Boolean>(false)

    fun login(username: String, psw: String) {
        launch {
            HttpBiz.getInstance().login(username, psw)
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
                        message.value = "登录出错"
                        loginValue.value = false
                    }
                    result.doSuccess { value ->
                        PrefUtil.flagLogin = true
                        PrefUtil.userName = value.username
                        loginValue.value = true
                    }
                }
        }
    }

    fun logout() {
        launch {
            HttpBiz.getInstance().logout()
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
                        message.value = "出错"
                        logoutValue.value = true
                    }
                    result.doSuccess { value ->
                        PrefUtil.flagLogin = false
                        PrefUtil.userName = ""
                        logoutValue.value = true
                    }
                }
        }
    }
    //收藏的文章
    fun getCollect() {
        launch {
            HttpBiz.getInstance().getMyCollectArticleList(0)
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
                        message.value = "getMainArticleList"
                    }
                    result.doSuccess { value ->
                        LogUtils.e("getMainArticleList", value)
                    }
                }
        }

    }
}