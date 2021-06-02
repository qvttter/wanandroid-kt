package com.li.mykotlinapp.base

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.base
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2018/10/23
 *@Copyright:(C)2018 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
data class BasePageResponse<T>(
        val data: PageDataBean<T>,
        val errorCode: Int,
        val errorMsg: String
) {
}