package com.li.mykotlinapp.biz

import com.li.mykotlinapp.base.BaseListResponse
import com.li.mykotlinapp.bean.BannerBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.biz
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/22
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
interface Service {
    @GET("banner/json")
    fun getMainBanner(): Observable<BaseListResponse<BannerBean>>

}