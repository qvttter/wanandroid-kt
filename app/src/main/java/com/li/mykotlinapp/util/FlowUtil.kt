package com.li.mykotlinapp.util

import com.li.mykotlinapp.base.*
import com.li.mykotlinapp.bean.jav.JavItemBean
import org.jsoup.Jsoup
import java.io.IOException

object FlowUtil {
    fun <T> getList(response: BaseListResponse<T>): CommonResult<List<T>> {
        if (response.errorCode==0){
            return CommonResult.Success(response.data)
        }
        return CommonResult.Failure(Throwable(response.errorMsg))
    }

    fun <T> getObject(response: BaseResponse<T>): CommonResult<T> {
        if (response.errorCode==0){
            return CommonResult.Success(response.data)
        }
        return CommonResult.Failure(Throwable(response.errorMsg))
    }

    fun <T> getPage(response: BasePageResponse<T>): CommonResult<PageDataBean<T>> {
        if (response.errorCode==0){
            return CommonResult.Success(response.data)
        }
        return CommonResult.Failure(Throwable(response.errorMsg))
    }

    fun handleMovieList(content:String): CommonResult<ArrayList<JavItemBean>>{
        try {
            var doc = Jsoup.parse(content)
            var body = doc.body()
            var containerFluid = body.select("div.container-fluid")
            var row = containerFluid.select("div.row")
            var waterfall = row.select("div#waterfall")
            var item = waterfall.select("div.item")
            var itemList = ArrayList<JavItemBean>()
            for(temp in item){
                var movieBox = temp.select("a.movie-box")
                //超链接
                var a = movieBox.select("a[href]")
                //封面
                var photoFrame = movieBox.select("div.photo-frame")
                var image = photoFrame.select("img[src]")
                //标题
                var photoInfo = movieBox.select("div.photo-info")
                var title = photoInfo.select("span")

                var bean = JavItemBean(a.attr("href"),PrefUtil.jUrl+image.attr("src"),title.text(),"","")


                itemList.add(bean)
            }
            return CommonResult.Success(itemList)


        } catch ( e: IOException) {
            e.printStackTrace()
            return CommonResult.Failure(Throwable("Jsoup handle error."+e.message))
        }

    }
}