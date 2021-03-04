package com.li.mykotlinapp.base

import java.util.*

/**
 * Author:  Ivan
 * Date:    Sep 08, 2017
 */

class BaseResponse<T>(
        val errorCode: Int,
        val errorMsg: String,
        var data: String) {


}
