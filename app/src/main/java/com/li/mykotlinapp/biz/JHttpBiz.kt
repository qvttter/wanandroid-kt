package com.li.mykotlinapp.biz

import com.li.mykotlinapp.base.BaseBiz
import com.li.mykotlinapp.base.CommonResult
import com.li.mykotlinapp.base.JBaseRetrofit
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.bean.jav.JavItemBean
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
 *@Date: 2022/4/25
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
class JHttpBiz private constructor() : JBaseRetrofit() {
    companion object {
        //https://www.seejav.co
        fun getInstance() = Holder.INSTANCE
    }

    private object Holder {
        val INSTANCE by lazy { JHttpBiz() }
    }

    private var service: JService = retrofit.create(JService::class.java)

    suspend fun getMosaicList(): Flow<CommonResult<List<JavItemBean>>> {
        return flow {
            var result =  service.getMosaicList()
            var infoModel =result.string()
            emit(FlowUtil.handleMovieList(infoModel))
        }.flowOn(Dispatchers.IO)
    }

}