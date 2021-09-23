package com.li.mykotlinapp.util

import com.li.mykotlinapp.base.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody

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
}