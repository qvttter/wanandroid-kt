package com.li.mykotlinapp.biz

import com.li.mykotlinapp.base.BaseBiz
import com.li.mykotlinapp.base.PageDataBean
import com.li.mykotlinapp.base.CommonResult
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.bean.LoginResponse
import com.li.mykotlinapp.util.FlowUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.biz
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23
 
 *************************************************************************/
class HttpBiz private constructor() : BaseBiz() {
    companion object {
        fun getInstance() = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE by lazy { HttpBiz() }
    }

    private var service: Service = retrofit.create(Service::class.java)

    suspend fun test(): Flow<CommonResult<String>> {
        return flow {
            var infoModel = service.test("https://tieba.baidu.com/hottopic/browse/topicList")
            emit(CommonResult.Success(infoModel.string()?:""))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMainBanner(): Flow<CommonResult<List<BannerBean>>> {
        return flow {
            var infoModel = service.getMainBanner()
            emit(FlowUtil.getList(infoModel))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun login(username: String, password: String): Flow<CommonResult<LoginResponse>> {
        return flow {
            var infoModel = service.login(username,password)
            emit(FlowUtil.getObject(infoModel))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun logout(): Flow<CommonResult<Any>> {
        return flow {
            var infoModel = service.logout()
            emit(FlowUtil.getObject(infoModel))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMyCollectArticleList(page: Int): Flow<CommonResult<PageDataBean<ArticleBean>>> {
        return flow {
            var infoModel = service.getMyCollectArticleList(page)
            emit(FlowUtil.getPage(infoModel))
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMainArticleList(page: Int): Flow<CommonResult<PageDataBean<ArticleBean>>> {
        return flow {
            var infoModel = service.getMainArticleList(page)
            emit(FlowUtil.getPage(infoModel))
        }.flowOn(Dispatchers.IO)
    }



}