package com.li.mykotlinapp.base

sealed class CommonResult <out T> {
    data class Success<out T>(val value: T) : CommonResult<T>()

    data class Failure(val throwable: Throwable?) : CommonResult<Nothing>()
}

inline fun <reified T> CommonResult<T>.doSuccess(success: (T) -> Unit) {
    if (this is CommonResult.Success) {
        success(value)
    }
}

inline fun <reified T> CommonResult<T>.doFailure(failure: (Throwable?) -> Unit) {
    if (this is CommonResult.Failure) {
        failure(throwable)
    }
}