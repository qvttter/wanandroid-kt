package com.li.mykotlinapp.biz

import com.li.mykotlinapp.base.BaseListResponse
import com.li.mykotlinapp.base.BasePageResponse
import com.li.mykotlinapp.base.BaseResponse
import com.li.mykotlinapp.bean.ArticleBean
import com.li.mykotlinapp.bean.BannerBean
import com.li.mykotlinapp.bean.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.biz
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2022/4/25
 *@Copyright:(C)2022 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
interface JService {
    @GET("/forum/member.php?mod=logging&action=login&referer=%2F%2Fwww.seejav.co%2F")
    suspend fun login(): ResponseBody

    //https://www.seejav.co/forum/member.php?mod=logging&action=login&loginsubmit=yes&loginhash=LPc83&inajax=1

    @GET("/page/{page}")
    suspend fun getMosaicList(@Path("page") page:Int): ResponseBody

    @GET("/{movieCode}")
    suspend fun getMovieDetail(@Path("movieCode") movieCode:String): ResponseBody

    @GET("/member/&mdl=favor&mod=ce&sort=0&page={page}")
    suspend fun getMyMosaicMovie(@Path("page") page:Int): ResponseBody

    @GET("article/list/{page}/json")
    suspend fun getMainArticleList(@Path("page") page: Int): BasePageResponse<ArticleBean>

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