package com.li.mykotlinapp.biz

import com.apkfuns.logutils.LogUtils
import com.li.mykotlinapp.base.BaseBiz
import com.li.mykotlinapp.base.PageDataBean
import com.li.mykotlinapp.base.BaseResult
import com.li.mykotlinapp.base.CommonResult
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.bean.LoginResponse
import com.li.mykotlinapp.util.FlowUtil
import com.li.mykotlinapp.util.RxUtil
import io.reactivex.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.biz
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class CommonBiz private constructor() : BaseBiz() {
    companion object {
        fun getInstance() = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE by lazy { CommonBiz() }
    }

    private var service: Service = retrofit.create(Service::class.java)

    suspend fun getMainBanner(): Flow<CommonResult<List<BannerBean>>> {
        return flow {
            var infoModel = service.getMainBanner()
            emit(FlowUtil.getList(infoModel))
        }.flowOn(Dispatchers.IO)
    }

    fun getMainArticleList(page: Int): Observable<PageDataBean<ArticleBean>> {
        return service.getMainArticleList(page)
            .compose(RxUtil.trans_io_main())
            .flatMap { t -> RxUtil.getPage(t) }
    }

    fun login(username: String, password: String): Observable<LoginResponse> {
        return service.login(username, password)
            .flatMap { t -> RxUtil.getObject(t) }
    }

}