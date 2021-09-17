package com.li.mykotlinapp.util

import com.li.mykotlinapp.base.BaseListResponse
import com.li.mykotlinapp.base.CommonResult
import com.li.mykotlinapp.base.doSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FlowUtil {
    fun <T> getList(response: BaseListResponse<T>): CommonResult<List<T>> {
        if (response.errorCode==0){
            return CommonResult.Success(response.data)
        }
        return CommonResult.Failure(Throwable(response.errorMsg))
    }

}