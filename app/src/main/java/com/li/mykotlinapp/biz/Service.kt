package com.li.mykotlinapp.biz

import com.li.mykotlinapp.base.BaseListResponse
import com.li.mykotlinapp.base.BasePageResponse
import com.li.mykotlinapp.base.BaseResponse
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.bean.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.biz
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/22
 
 *************************************************************************/
interface Service {
    @GET
    suspend fun test(@Url url:String): ResponseBody

    @GET("banner/json")
    suspend fun getMainBanner(): BaseListResponse<BannerBean>

    @GET("article/list/{page}/json")
    suspend fun getMainArticleList(@Path("page") page: Int): BasePageResponse<ArticleBean>

    //收藏的文章
    @GET("lg/collect/list/{page}/json")
    suspend fun getMyCollectArticleList(@Path("page") page: Int): BasePageResponse<ArticleBean>

    @GET("lg/collect/list/{page}/json")
    fun getCollect(@Path("page") page: Int): BasePageResponse<ArticleBean>

    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String)
            : BaseResponse<LoginResponse>

    @GET("user/logout/json")
    suspend fun logout(): BaseResponse<Any>

}