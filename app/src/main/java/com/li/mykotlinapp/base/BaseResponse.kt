package com.li.mykotlinapp.base

import java.util.*

/**
 * Author:  Ivan
 * Date:    Sep 08, 2017
 */

data class BaseResponse<out T>(
        val errorCode: Int,
        val errorMsg: String,
        val data: T) {


}
