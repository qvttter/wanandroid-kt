package com.li.mykotlinapp.biz

import com.li.mykotlinapp.base.BaseListResponse
import com.li.mykotlinapp.base.BaseObjectResponse
import com.li.mykotlinapp.base.BasePageResponse
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.bean.LoginResponse
import io.reactivex.Observable
import retrofit2.http.*

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

    @GET("article/list/{page}/json")
    fun getMainArticleList(@Path("page") page: Int): Observable<BasePageResponse<ArticleBean>>

    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("username") username: String, @Field("password") password: String)
            : Observable<BaseObjectResponse<LoginResponse>>

}