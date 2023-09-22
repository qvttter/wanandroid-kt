package com.li.mykotlinapp.biz

import com.li.mykotlinapp.base.BaseListResponse
import com.li.mykotlinapp.base.BasePageResponse
import com.li.mykotlinapp.base.BaseResponse
import com.li.mykotlinapp.bean.*
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

    /**
     * 首页banner
     */
    @GET("banner/json")
    suspend fun getMainBanner(): BaseListResponse<BannerBean>

    /**
     * 文章
     * cid 分类，不传就返回首页文章。传了就返回知识体系下的文章
     * author 按照作者昵称搜索文章 ?cid={cid}&author={author} ,@Path("cid") cid: Int,@Path("author") author:String
     */
    @GET("article/list/{page}/json")
    suspend fun getMainArticleList(@Path("page") page: Int): BasePageResponse<ArticleBean>

    /**
     * 收藏的文章
     */
    @GET("lg/collect/list/{page}/json")
    suspend fun getMyCollectArticleList(@Path("page") page: Int): BasePageResponse<ArticleBean>

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String)
            : BaseResponse<LoginResponse>

    /**
     * 登出
     */
    @GET("user/logout/json")
    suspend fun logout(): BaseResponse<Any>

    /**
     * 常用网站
     */
    @GET("friend/json")
    suspend fun commonWebsites(): BaseResponse<BaseListResponse<CommonWebsiteBean>>

    /**
     * 搜索热词
     */
    @GET("hotkey/json")
    suspend fun searchHotkey(): BaseResponse<BaseListResponse<SearchHotKeyBean>>

    /**
     * 置顶文章
     */
    @GET("article/top/json")
    suspend fun topArticle(): BaseResponse<BaseListResponse<ArticleBean>>

    /**
     * 体系数据
     */
    @GET("tree/json")
    suspend fun treeList(): BaseResponse<BaseListResponse<TreeBean>>

    /**
     * 导航数据
     */
    @GET("tree/json")
    suspend fun naviList(): BaseResponse<BaseListResponse<TreeBean>>
}