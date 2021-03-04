package com.li.mykotlinapp.base

/************************************************************************
 * @Project: rx-iim-app
 * @Package_Name: com.easyway.rx_iim_app.data.http
 * @Descriptions:
 * @Author: zhouli
 * @Date: 2018/7/27
 * @Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 */
class BaseObjectResponse<T> (
        val errorCode: Int,
        val errorMsg: String,
        var data: T?) {

}
